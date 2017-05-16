(ns geek-report.driver.h2
  (:require [geek-report.driver.core :as driver]
            [geek-report.driver.generic-sql :as sql-driver]
            [geek-report.util :as util]))

(defn h2-spec
  "Create a database specification for a h2 database. Opts should include a key
  for :db which is the path to the database file."
  [{:keys [db]
    :or {db "h2.db"}
    :as opts}]
  (merge {:classname "org.h2.Driver" ; must be in classpath
          :subprotocol "h2"
          :subname db}
         (dissoc opts :db)))

(defn connection-details->spec [details]
  "detail-map -> jdbc-connection-dpec"
  (h2-spec details))

(defrecord H2Driver []
  clojure.lang.Named
  (getName [_] "MySQL"))

(extend H2Driver
  driver/IDriver
  (sql-driver/ISQLDriverDefaultMixins)
  sql-driver/ISQLDriver
  {:connection-details->spec (util/drop-first-arg connection-details->spec)})

(driver/register-driver! :h2 (H2Driver.))
