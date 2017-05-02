(ns geek-report.schema
  (:require [schema.core :as s]))

(s/defschema ReturnModel {:status              Long
                          :message             String
                          (s/optional-key :id) s/Any})

(s/defschema Datasource {(s/optional-key :id)         Long
                         :name                        String
                         :dbname                      String
                         :driver_path                 String
                         :config                      String
                         (s/optional-key :created_at) s/Any
                         (s/optional-key :project_id) Long})

(s/defschema DatasourceList [Datasource])

(s/defschema Project {(s/optional-key :id)         Long
                      :title                       String
                      (s/optional-key :created_by) Long
                      (s/optional-key :created_at) s/Any})

(s/defschema ProjectList [Project])
