(ns geek-report.routes.apis
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [compojure.api.meta :refer [restructure-param]]
            [buddy.auth.accessrules :refer [restrict]]
            [buddy.auth :refer [authenticated?]]
            [geek-report.services.core :as service]
            [geek-report.schema :as schema])
  (:import (java.sql Timestamp)))

(defn access-error [_ _]
  (unauthorized {:error "unauthorized"}))

(defn wrap-restricted [handler rule]
  (restrict handler {:handler  rule
                     :on-error access-error}))

(defmethod restructure-param :auth-rules
  [_ rule acc]
  (update-in acc [:middleware] conj [wrap-restricted rule]))

(defmethod restructure-param :current-user
  [_ binding acc]
  (update-in acc [:letks] into [binding `(:identity ~'+compojure-api-request+)]))



(defapi api-routes
  {:swagger {:ui   "/swagger-ui"
             :spec "/swagger.json"
             :data {:info {:version     "1.0.0"
                           :title       "GeekReport Restful APIs"
                           :description "Restful Services"}}}}

  (GET "/authenticated" []
    :auth-rules authenticated?
    :current-user user
    (ok {:user user}))
  (context "/api" []
    :tags ["services"]

    (context "/datasource" []
      :tags ["datasource"]
      (POST "/" []
        :return schema/ReturnModel
        :body-params [name :- String,
                      dbname :- String
                      driver_path :- String
                      config :- String
                      project_id :- Long]
        :summary "新建数据源"
        (service/create-datasource! {:name        name
                                     :dbname      dbname
                                     :driver_path driver_path
                                     :config      config
                                     :project_id  project_id}))
      (GET "/" []
        :return {:status  Long
                 :message String
                 :data    schema/DatasourceList}
        :query-params [project_id :- Long]
        :summary "获取项目下的数据源"
        (service/get-datasources project_id))
      (GET "/:id" req
        :return {:status  Long
                 :message String
                 :data    schema/Datasource}
        :path-params [id :- Long]
        :summary "获取数据源"
        (service/get-datasource id))
      (PATCH "/" req
        :return schema/ReturnModel
        :body-params [id :- Long
                      name :- String,
                      dbname :- String
                      driver_path :- String
                      config :- String]
        :summary "更新数据源"
        (service/update-datasource! {:id          id
                                     :name        name
                                     :dbname      dbname
                                     :driver_path driver_path
                                     :config      config}))
      (DELETE "/:id" req
        :return schema/ReturnModel
        :path-params [id :- Long]
        :summary "更新数据源"
        (service/delete-datasource! id)))

    (context "/project" []
      :tags ["project"]
      (POST "/" []
        :return schema/ReturnModel
        :body-params [title :- String, created_by :- Long]
        :summary "新建项目"
        (service/create-project! {:title title :created_by created_by}))
      (GET "/" []
        :return {:status  Long
                 :message String
                 :data    schema/ProjectList}
        :query-params [user_id :- Long]
        :summary "获取用户的所有项目"
        (service/get-projects user_id))
      (GET "/:id" []
        :return {:status  Long
                 :message String
                 :data    schema/Project}
        :path-params [id :- Long]
        :summary "项目详情"
        (service/get-project id))
      (PATCH "/" []
        :return schema/ReturnModel
        :body-params [title :- String]
        :summary "修改项目"
        (service/update-project! {:title title}))
      (DELETE "/:id" []
        :return schema/ReturnModel
        :path-params [id :- Long]
        :summary "删除项目"
        (service/delete-project! id)))
    ))
