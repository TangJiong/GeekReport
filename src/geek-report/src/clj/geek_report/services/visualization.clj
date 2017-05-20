(ns geek-report.services.visualization
  (:require [geek-report.db.core :as db]
            [geek-report.driver.core :as driver]
            [ring.util.http-response :refer :all]
            [clojure.tools.logging :as log])
  (:import [java.util Date]))

;;; ======================= service of visualization ============================
(defn create-visualization! [visualization]
  "新增项目"
  (let [visualization (assoc visualization :created_at (java.util.Date.))]
    (try
      (let [result (db/create-visualization! visualization)
            id (second result)]
        (ok {:status  200
             :message "SUCCESS"
             :id      id}))
      (catch Exception e
        (internal-server-error {:status  500
                                :message (.getMessage e)})))))

(defn get-visualizations [query-id]
  "获取用户的所有项目"
  (let [visualizations (db/get-visualizations-by-query {:query_id query-id})]
    (ok {:status  200
         :message "SUCCESS"
         :data    visualizations})))

(defn get-visualization [id]
  "项目详情"
  (let [visualization (db/get-visualization-by-id {:id id})]
    (ok {:status  200
         :message "SUCCESS"
         :data    visualization})))

(defn update-visualization! [visualization]
  "更新项目"
  (try
    (db/update-visualization! visualization)
    (ok {:status  200
         :message "SUCCESS"})
    (catch Exception e
      (internal-server-error {:status  500
                              :message (.getMessage e)}))))

(defn delete-visualization! [id]
  "删除项目"
  (try
    (db/delete-visualization! {:id id})
    (ok {:status  200
         :message "SUCCESS"})
    (catch Exception e
      (internal-server-error {:status  500
                              :message (.getMessage e)}))))