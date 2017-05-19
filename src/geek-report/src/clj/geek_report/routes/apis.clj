(ns geek-report.routes.apis
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [compojure.api.meta :refer [restructure-param]]
            [buddy.auth.accessrules :refer [restrict]]
            [buddy.auth :refer [authenticated?]]
            [geek-report.services
             [project :as project-service]
             [datasource :as datasource-service]
             [paragraph :as paragraph-service]
             [query :as query-service]]
            [geek-report.schema :as schema])
  (:import (java.sql Timestamp)
           (java.util Map)))

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

    ;;; ========================================================================
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
        (datasource-service/create-datasource! {:name        name
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
        (datasource-service/get-datasources project_id))
      (GET "/:id" req
        :return {:status  Long
                 :message String
                 :data    schema/Datasource}
        :path-params [id :- Long]
        :summary "获取数据源"
        (datasource-service/get-datasource id))
      (PATCH "/" req
        :return schema/ReturnModel
        :body-params [id :- Long
                      name :- String,
                      dbname :- String
                      driver_path :- String
                      config :- String]
        :summary "更新数据源"
        (datasource-service/update-datasource! {:id          id
                                                :name        name
                                                :dbname      dbname
                                                :driver_path driver_path
                                                :config      config}))
      (DELETE "/:id" req
        :return schema/ReturnModel
        :path-params [id :- Long]
        :summary "更新数据源"
        (datasource-service/delete-datasource! id)))

    ;;; ========================================================================
    (context "/project" []
      :tags ["project"]
      (POST "/" []
        :return schema/ReturnModel
        :body-params [title :- String, created_by :- Long]
        :summary "新建项目"
        (project-service/create-project! {:title title :created_by created_by}))
      (GET "/" []
        :return {:status  Long
                 :message String
                 :data    schema/ProjectList}
        :query-params [user_id :- Long]
        :summary "获取用户的所有项目"
        (project-service/get-projects user_id))
      (GET "/:id" []
        :return {:status  Long
                 :message String
                 :data    schema/Project}
        :path-params [id :- Long]
        :summary "项目详情"
        (project-service/get-project id))
      (PATCH "/" []
        :return schema/ReturnModel
        :body-params [id :- Long
                      title :- String]
        :summary "修改项目"
        (project-service/update-project! {:id    id
                                          :title title}))
      (DELETE "/:id" []
        :return schema/ReturnModel
        :path-params [id :- Long]
        :summary "删除项目"
        (project-service/delete-project! id)))

    ;;; ========================================================================
    (context "/query" []
      :tags ["query"]
      (POST "/run" []
        :return s/Any
        :body-params [paragraph_id :- Long
                      datasource_id :- Long
                      lang :- String
                      raw :- String]
        :summary "执行查询"
        (query-service/run-query {:datasource-id datasource_id
                                  :query-text    raw}))
      (POST "/save" []
        :return schema/ReturnModel
        :body-params [id :- Long                            ;;; -1 for new query
                      paragraph_id :- Long
                      datasource_id :- Long
                      lang :- String
                      raw :- String
                      max_age :- Long]
        :summary "执行查询"
        (query-service/save-query! {:id            id
                                    :paragraph_id  paragraph_id
                                    :datasource_id datasource_id
                                    :lang          lang
                                    :raw           raw
                                    :max_age       max_age}))
      (PATCH "/maxage" []
        :return schema/ReturnModel
        :body-params [id :- Long
                      max_age :- Long]
        :summary "执行查询"
        (query-service/set-query-maxage! id max_age))
      (GET "/" []
        :return {:status  Long
                 :message String
                 :data    s/Any}
        :query-params [paragraph_id :- Long]
        :summary "获取段落对应的查询"
        (query-service/get-query-by-paragraph paragraph_id))
      (DELETE "/:id" []
        :return schema/ReturnModel
        :path-params [id :- Long]
        :summary "删除查询"
        (query-service/delete-query! id)))

    ;;; ========================================================================
    (context "/paragraph" []
      :tags ["paragraph"]
      (POST "/" []
        :return schema/ReturnModel
        :body-params [title :- String,
                      project_id :- Long]
        :summary "新建段落"
        (paragraph-service/create-paragraph! {:title      title
                                              :project-id project_id}))
      (GET "/" []
        :return {:status  Long
                 :message String
                 :data    schema/ParagraphList}
        :query-params [project_id :- Long]
        :summary "获取一个项目的所有段落"
        (paragraph-service/get-paragraphs project_id))
      (GET "/:id" []
        :return {:status  Long
                 :message String
                 :data    schema/Paragraph}
        :path-params [id :- Long]
        :summary "段落详情"
        (paragraph-service/get-paragraph id))
      (PATCH "/" []
        :return schema/ReturnModel
        :body-params [id :- Long
                      title :- String,
                      width :- Long,
                      height :- Long,
                      index :- Long,
                      default_visual_id :- Long]
        :summary "修改项目"
        (paragraph-service/update-paragraph! {:id                id
                                              :title             title
                                              :width             width
                                              :height            height
                                              :index             index
                                              :default_visual_id default_visual_id}))
      (DELETE "/:id" []
        :return schema/ReturnModel
        :path-params [id :- Long]
        :summary "删除段落"
        (paragraph-service/delete-paragraph! id)))
    ))
