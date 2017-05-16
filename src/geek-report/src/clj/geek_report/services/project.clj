(ns geek-report.services.project
  (:require [geek-report.db.core :as db]
            [ring.util.http-response :refer :all]
            [clojure.tools.logging :as log]))

;;; ======================= service of project ============================
(defn create-project! [project]
  "新增项目"
  (let [project (assoc project :created_at (java.util.Date.))]
    (try
      (let [result (db/create-project! project)
            id (second result)]
        (ok {:status  200
             :message "SUCCESS"
             :id      id}))
      (catch Exception e
        (internal-server-error {:status  500
                                :message (.getMessage e)})))))

(defn get-projects [user-id]
  "获取用户的所有项目"
  (let [projects (db/get-projects-by-user {:user_id user-id})]
    (ok {:status  200
         :message "SUCCESS"
         :data    projects})))

(defn get-project [id]
  "项目详情"
  (let [project (db/get-project-by-id {:id id})]
    (ok {:status  200
         :message "SUCCESS"
         :data    project})))

(defn update-project! [project]
  "更新项目"
  (try
    (db/update-project! project)
    (ok {:status  200
         :message "SUCCESS"})
    (catch Exception e
      (internal-server-error {:status  500
                              :message (.getMessage e)}))))

(defn delete-project! [id]
  "删除项目"
  (try
    (db/delete-project! {:id id})
    (ok {:status  200
         :message "SUCCESS"})
    (catch Exception e
      (internal-server-error {:status  500
                              :message (.getMessage e)}))))
