(ns geek-report.services.paragraph
  (:require [geek-report.db.core :as db]
            [ring.util.http-response :refer :all]
            [clojure.tools.logging :as log])
  (:import [java.util Date]))

(defn- max-paragraph-index-of-project [project-id]
  (or (->
        (db/get-max-index-in-project {:project_id project-id})
        (:max_index))
      0))

(defn create-paragraph! [{:keys [title project-id width height index default-visual-id]}]
  (try
    (let [width (or width 24)
          height (or height 1)
          index (or index
                    (->
                      (max-paragraph-index-of-project project-id)
                      (+ 1)))
          default-visual-id (or default-visual-id -1)
          paragraph {:title             title
                     :project_id        project-id
                     :width             width
                     :height            height
                     :index             index
                     :default_visual_id default-visual-id
                     :created_at        (Date.)}
          result (db/create-paragraph! paragraph)
          id (second result)]
      (ok {:status  200
           :message "SUCCESS"
           :id      id}))
    (catch Exception e
      (log/error (.getMessage e))
      (internal-server-error {:status  500
                              :message (.getMessage e)}))))

(defn get-paragraphs [project-id]
  (let [paragraphs (db/get-paragraph-by-project {:project_id project-id})]
    (ok {:status  200
         :message "SUCCESS"
         :data      paragraphs})))

(defn get-paragraph [id]
  (let [paragraph (db/get-paragraph-by-id {:id id})]
    (ok {:status  200
         :message "SUCCESS"
         :data      paragraph})))

(defn update-paragraph! [paragraph]
  (try
    (db/update-paragraph! paragraph)
    (ok {:status  200
         :message "SUCCESS"})
    (catch Exception e
      (log/error (.getMessage e))
      (internal-server-error {:status  500
                              :message (.getMessage e)}))))

(defn delete-paragraph! [id]
  (try
    (db/delete-paragraph! {:id id})
    (ok {:status  200
         :message "SUCCESS"})
    (catch Exception e
      (log/error (.getMessage e))
      (internal-server-error {:status  500
                              :message (.getMessage e)}))))

;(defn max [project-id]
;  (try
;    (let [max (db/get-max-index-in-project {:project_id project-id})]
;      (println max)
;      (ok {:status  200
;           :message "SUCCESS"
;           :data    max}))
;    (catch Exception e
;      (log/error (.getMessage e))
;      (internal-server-error {:status  500
;                              :message (.getMessage e)}))))
