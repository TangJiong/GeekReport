(ns geek-report.services.query
  (:require [geek-report.db.core :as db]
            [geek-report.driver.core :as driver]
            [ring.util.http-response :refer :all]
            [clojure.tools.logging :as log]
            [cheshire.core :as json]))


;;; ======================= service of query =====================

(defn- datasource->db-spec [{:keys [config]}]
  "将数据源信息转换成db连接信息"
  (json/parse-string config true))

(defn run-query [{:keys [datasource-id project-id query-text]}]
  "执行查询任务"
  (try
    (let [datasource (db/get-datasource-by-id {:id datasource-id})
          db-name (:dbname datasource)
          db-spec (datasource->db-spec datasource)
          db-driver (driver/load-driver (keyword db-name))
          query {:datasource db-spec
                 :query query-text}
          result-fn (driver/execute-query db-driver query)
          result (result-fn)]
      (ok {:status 200
           :message "SUCCESS"
           :data result}))
    (catch Exception e
      (log/error (.getMessage e)))))
