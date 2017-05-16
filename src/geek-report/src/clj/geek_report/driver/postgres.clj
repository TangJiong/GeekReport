(ns geek-report.driver.postgres
  (:require [geek-report.driver.core :as driver]
            [geek-report.driver.generic-sql :as sql-driver]
            [geek-report.util :as util]))

(defn postgres-spec
  "Create a database specification for a postgres database. Opts should include
  keys for :db, :user, and :password. You can also optionally set host and
  port."
  [{:keys [host port db]
    :or {host "localhost", port 5432, db ""}
    :as opts}]
  (merge {:classname "org.postgresql.Driver" ; must be in classpath
          :subprotocol "postgresql"
          :subname (str "//" host ":" port "/" db)}
         (dissoc opts :host :port :db)))

(defn connection-details->spec [details]
  "detail-map -> jdbc-connection-dpec"
  (postgres-spec details))

(defrecord PostgreSQLDriver []
  clojure.lang.Named
  (getName [_] "PostgreSQL"))

(extend PostgreSQLDriver
  driver/IDriver
  (sql-driver/ISQLDriverDefaultMixins)
  sql-driver/ISQLDriver
  {:connection-details->spec (util/drop-first-arg connection-details->spec)})

(driver/register-driver! :postgres (PostgreSQLDriver.))
