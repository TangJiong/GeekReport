(ns geek-report.services.query
  (:require [geek-report.db.core :as db]
            [geek-report.driver.core :as driver]
            [ring.util.http-response :refer :all]
            [clojure.tools.logging :as log]
            [cheshire.core :as json])
  (:import [java.util Date]))


;;; ======================= service of query =====================

(defn- datasource->db-spec [{:keys [config]}]
  "将数据源信息转换成db连接信息"
  (json/parse-string config true))

(defn run-query [{:keys [datasource-id query-text]}]
  "执行查询任务"
  (try
    (let [datasource (db/get-datasource-by-id {:id datasource-id})
          db-name (:dbname datasource)
          db-spec (datasource->db-spec datasource)
          db-driver (driver/load-driver (keyword db-name))
          query {:datasource db-spec
                 :query      query-text}
          result-fn (driver/execute-query db-driver query)
          result (result-fn)]
      (ok {:status  200
           :message "SUCCESS"
           :data    result}))
    (catch Exception e
      (log/error (.getMessage e))
      (internal-server-error {:status  500
                              :message (.getMessage e)}))))

(defn save-query! [query]
  "保存查询：新增或者更新"
  (try
    (let [id (:id query)
          result (if (= id -1)
                   (let [query (->
                                 query
                                 (dissoc :id)
                                 (assoc :created_at (Date.)))]
                     (db/create-query! query))
                   (let [query (dissoc query :paragraph_id :datasource-id)]
                     (db/update-query! query)
                     nil))]
      (ok {:status  200
           :message "SUCCESS"
           :id      (second result)}))
    (catch Exception e
      (log/error (.getMessage e))
      (internal-server-error {:status  500
                              :message (.getMessage e)}))))

(defn get-query-by-paragraph [paragraph-id]
  "根据段落id查询"
  (let [query (db/get-query-by-paragraph {:paragraph_id paragraph-id})]
    (ok {:status  200
         :message "SUCCESS"
         :data    query})))

(defn delete-query!
  "删除query"
  [query-id]
  (try
    (db/delete-query! {:id query-id})
    (ok {:status  200
         :message "SUCCESS"})
    (catch Exception e
      (log/error (.getMessage e)))))
