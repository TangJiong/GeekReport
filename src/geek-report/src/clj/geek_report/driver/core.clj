(ns geek-report.driver.core
  (:require [clojure.tools.logging :as log])
  (:import [clojure.lang Keyword]))

(defprotocol IDriver
  "Methods that GeekReport drivers must implement"
  (can-connect? ^Boolean [this, ^java.util.Map details-map]
    "Check whether we can connect to a `Database` with DETAILS-MAP and perform a simple query. For example, a SQL database might
     try running a query like `SELECT 1;`. This function should return `true` or `false`.")
  (execute-query ^java.util.Map [this, ^java.util.Map query]
    "Execute a query against the database and return the results.

  The query passed in will contain:

         {:database ^DatabaseInstance
          :native   {... driver specific query form such as one returned from a call to `mbql->native` ...}
          :settings {:report-timezone \"US/Pacific\"
                     :other-setting   \"and its value\"}}

  Results should look like:

         {:columns [\"id\", \"name\"]
          :rows    [[1 \"Lucky Bird\"]
                    [2 \"Rasta Can\"]]}"))

(defonce ^:private registered-drivers
         (atom {}))

(defn register-driver!
  "Register a DRIVER, an instance of a class that implements `IDriver`, for ENGINE.

     (register-driver! :postgres (PostgresDriver.))"
  [^Keyword engine, driver-instance]
  {:pre [(keyword? engine) (map? driver-instance)]}
  (swap! registered-drivers assoc engine driver-instance)
  (log/debug (format "Registered driver %s" engine)))

(defn load-driver
  "Return the driver instance that should be used for given ENGINE keyword.
   This loads the corresponding driver if needed; this is done with a call like

     (require 'metabase.driver.<engine>)

   The namespace itself should register itself by passing an instance of a class that
   implements `IDriver` to `metabase.driver/register-driver!`."
  [engine]
  (or ((keyword engine) @registered-drivers)
      (let [namespce (symbol (format "geek-report.driver.%s" (name engine)))]
        (require namespce)
        ((keyword engine) @registered-drivers))))