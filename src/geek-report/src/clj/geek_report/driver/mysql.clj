(ns geek-report.driver.mysql
  (:require [geek-report.driver.core :as driver]
            [geek-report.driver.generic-sql :as sql-driver]
            [geek-report.util :as util]))

(defn mysql-spec
  "Create a database specification for a mysql database. Opts should include keys
  for :db, :user, and :password. You can also optionally set host and port.
  Delimiters are automatically set to \"`\"."
  [{:keys [host port db]
    :or {host "localhost", port 3306, db ""}
    :as opts}]
  (merge {:classname "com.mysql.jdbc.Driver" ; must be in classpath
          :subprotocol "mysql"
          :subname (str "//" host ":" port "/" db)
          :delimiters "`"}
         (dissoc opts :host :port :db)))

(defn connection-details->spec [details]
  "detail-map -> jdbc-connection-dpec"
  (mysql-spec details))


(defrecord MySQLDriver []
  clojure.lang.Named
  (getName [_] "MySQL"))

(extend MySQLDriver
  driver/IDriver
  (sql-driver/ISQLDriverDefaultMixins)
  sql-driver/ISQLDriver
  {:connection-details->spec (util/drop-first-arg connection-details->spec)})

(driver/register-driver! :mysql (MySQLDriver.))