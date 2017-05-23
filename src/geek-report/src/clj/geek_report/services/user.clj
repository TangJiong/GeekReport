(ns geek-report.services.user
  (:require [geek-report.db.core :as db]
            [ring.util.http-response :refer :all]
            [clojure.tools.logging :as log]))

;;; ======================= service of project ============================

(defn- user-exist? [email]
  (let [user (db/get-user-by-email {:email email})]
    (not (nil? user))))


(defn create-user! [user]
  "新增用户"
  (let [exist? (user-exist? (:email user))]
    (if exist?
      (ok {:status  500
           :message "该账户已存在"})
      (try
        (let [result (db/create-user! user)
              id (second result)]
          (ok {:status  200
               :message "SUCCESS"
               :id      id}))
        (catch Exception e
          (internal-server-error {:status  500
                                  :message (.getMessage e)}))))))

(defn login [user]
  "用户登录"
  (let [_user (db/get-user-by-email (select-keys user [:email]))]
    (if (nil? _user)
      (ok {:status  404
           :message "用户不存在"})
      (if (= (:password _user) (:password user))
        (ok {:status  200
             :message "SUCCESS"
             :data (dissoc _user :password)})
        (ok {:status  400
             :message "密码错误"})))))

