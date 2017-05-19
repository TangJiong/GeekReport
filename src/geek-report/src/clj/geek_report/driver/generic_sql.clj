(ns geek-report.driver.generic-sql
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.tools.logging :as log])
  (:import java.util.Map
           (java.sql SQLException)))

(defprotocol ISQLDriver
  "Methods SQL-based drivers should implement."
  (connection-details->spec [this, ^Map details-map]
    "Given a `Database` DETAILS-MAP, return a JDBC connection spec."))

(defn- can-connect? [driver details]
  (let [connection (connection-details->spec driver details)]
    (= 1 (first (vals (first (jdbc/query connection ["SELECT 1"])))))))

(defn- do-with-auto-commit-disabled
  "Disable auto-commit for this transaction, and make the transaction `rollback-only`, which means when the transaction finishes `.rollback` will be called instead of `.commit`.
   Furthermore, execute F in a try-finally block; in the `finally`, manually call `.rollback` just to be extra-double-sure JDBC any changes made by the transaction aren't committed."
  {:style/indent 1}
  [conn f]
  (jdbc/db-set-rollback-only! conn)
  (.setAutoCommit (jdbc/get-connection conn) false)
  ;; TODO - it would be nice if we could also `.setReadOnly` on the transaction as well, but that breaks setting the timezone. Is there some way we can have our cake and eat it too?
  (try (f)
       (finally (.rollback (jdbc/get-connection conn)))))

(defn- do-in-transaction [connection f]
  (jdbc/with-db-transaction [transaction-connection connection]
                            (do-with-auto-commit-disabled transaction-connection (partial f transaction-connection))))

(defn- vec-vec->dict-vec
  [rows columns]
  (map (fn [row]
         (zipmap (map #(keyword %) columns) row)) rows))

(defn- run-query
  "Run the query itself."
  [query connection]
  (let [[columns & rows] (jdbc/query connection query {:identifiers identity :as-arrays? true})]
    {:columns columns
     :rows    (or (vec-vec->dict-vec rows columns) [])}))

(defn- execute-query
  "Process and run a native (raw SQL) QUERY."
  [driver {:keys [datasource query]}]
  (try
    (fn []
      (let [db-connection (connection-details->spec driver datasource)]
        (do-in-transaction db-connection (fn [transaction-connection]
                                           (run-query query transaction-connection)))))
    (catch SQLException e
      (log/error (str "===>" (.getMessage e))))))

(defn ISQLDriverDefaultMixins []
  "default implementation of generic sql method"
  {:can-connect?  can-connect?
   :execute-query execute-query})






