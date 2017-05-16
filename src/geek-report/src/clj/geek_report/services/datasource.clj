(ns geek-report.services.datasource
  (:require [geek-report.db.core :as db]
            [ring.util.http-response :refer :all]
            [clojure.tools.logging :as log]))

;;; ======================== service of datasource ==========================
(defn create-datasource! [datasource]
  "新增数据源"
  (let [datasource (assoc datasource :created_at (java.util.Date.))]
    (try
      (let [result (db/create-datasource! datasource)
            id (second result)]
        (ok {:status  200
             :message "SUCCESS"
             :id      id}))
      (catch Exception e
        (internal-server-error {:status  500
                                :message (.getMessage e)})))))

(defn get-datasources [project-id]
  "获取项目下的所有数据源"
  (let [datasources (db/get-datasources-by-project {:project_id project-id})]
    (ok {:status  200
         :message "SUCCESS"
         :data    datasources})))

(defn get-datasource [id]
  "数据源详情"
  (let [datasource (db/get-datasource-by-id {:id id})]
    (ok {:status  200
         :message "SUCCESS"
         :data    datasource})))

(defn update-datasource! [datasource]
  "更新数据源"
  (try
    (db/update-datasource! datasource)
    (ok {:status  200
         :message "SUCCESS"})
    (catch Exception e
      (internal-server-error {:status  500
                              :message (.getMessage e)}))))

(defn delete-datasource! [id]
  "删除数据源"
  (try
    (db/delete-datasource! {:id id})
    (ok {:status  200
         :message "SUCCESS"})
    (catch Exception e
      (internal-server-error {:status  500
                              :message (.getMessage e)}))))