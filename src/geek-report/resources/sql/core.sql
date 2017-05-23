-- :name create-datasource! :i! :n
-- :doc create a new datasource record
INSERT INTO datasource
(name, driver_path, dbname, config, project_id, created_at)
VALUES (:name, :driver_path, :dbname, :config, :project_id, :created_at)

-- :name get-datasources-by-project :? :*
-- :doc get datasources of a project
SELECT * FROM datasource
WHERE project_id = :project_id

-- :name get-datasource-by-id :? :1
-- :doc get datasource
SELECT * FROM datasource
WHERE id = :id

-- :name update-datasource! :! :n
-- :doc update datasource
UPDATE datasource
SET name = :name, driver_path = :driver_path, dbname = :dbname, config = :config
WHERE id = :id

-- :name delete-datasource! :! :n
-- :doc delete datasource
DELETE FROM datasource
WHERE id = :id



-- :name create-project! :i! :n
-- :doc create a new project record
INSERT INTO project
(title, created_by, created_at)
VALUES (:title, :created_by, :created_at)

-- :name get-projects-by-user :? :*
-- :doc get projects of a user
SELECT * FROM project
WHERE created_by = :user_id
ORDER BY created_at DESC

-- :name get-project-by-id :? :1
-- :doc get project
SELECT * FROM project
WHERE id = :id

-- :name update-project! :! :n
-- :doc update project
UPDATE project
SET title = :title
WHERE id = :id

-- :name delete-project! :! :n
-- :doc delete project
DELETE FROM project
WHERE id = :id


-- :name create-paragraph! :i! :n
-- :doc create a new paragraph record
INSERT INTO paragraph
(title, project_id, width, height, `index`, default_visual_id, created_at)
VALUES (:title, :project_id, :width, :height, :index, :default_visual_id, :created_at)

-- :name get-paragraph-by-project :? :*
-- :doc get paragraph of a project order by index asc
SELECT * FROM paragraph
WHERE project_id = :project_id
ORDER BY `index`

-- :name get-paragraph-by-id :? :1
-- :doc get paragraph detail
SELECT * FROM paragraph
WHERE id = :id

-- :name update-paragraph! :! :n
-- :doc update paragraph
UPDATE paragraph
SET title = :title, width = :width, height = :height, `index` = :index, default_visual_id = :default_visual_id
WHERE id = :id

-- :name delete-paragraph! :! :n
-- :doc delete paragraph
DELETE FROM paragraph
WHERE id = :id

-- :name get-max-index-in-project :? :1
-- :doc get max index of paragraph in a project
SELECT MAX(`index`) AS max_index FROM paragraph
WHERE project_id = :project_id


-- :name create-query! :i! :n
-- :doc create a new query record
INSERT INTO query
(paragraph_id, datasource_id, lang, raw, max_age, created_at)
VALUES (:paragraph_id, :datasource_id, :lang, :raw, :max_age, :created_at)

-- :name get-query-by-paragraph :? :1
-- :doc get query of a paragraph
SELECT * FROM query
WHERE paragraph_id = :paragraph_id

-- :name get-query-by-id :? :1
-- :doc get query detail
SELECT * FROM query
WHERE id = :id

-- :name update-query! :! :n
-- :doc update query
UPDATE query
SET lang = :lang, raw = :raw, max_age = :max_age
WHERE id = :id

-- :name update-query-maxage! :! :n
-- :doc update query max_age
UPDATE query
SET max_age = :max_age
WHERE id = :id

-- :name delete-query! :! :n
-- :doc delete query
DELETE FROM query
WHERE id = :id


-- :name create-visualization! :i! :n
-- :doc create a new visualization record
INSERT INTO visualization
(query_id, title, chart_type, group_column, observe_column, measure_column, created_at)
VALUES (:query_id, :title, :chart_type, :group_column, :observe_column, :measure_column, :created_at)

-- :name get-visualizations-by-query :? :*
-- :doc get visualization of a query
SELECT * FROM visualization
WHERE query_id = :query_id

-- :name get-visualization-by-id :? :1
-- :doc get visualization
SELECT * FROM visualization
WHERE id = :id

-- :name update-visualization! :! :n
-- :doc update visualization
UPDATE visualization
SET title = :title, chart_type = :chart_type, group_column = :group_column,
 observe_column = :observe_column, measure_column = :measure_column
WHERE id = :id

-- :name delete-visualization! :! :n
-- :doc delete visualization
DELETE FROM visualization
WHERE id = :id


-- :name create-user! :i! :n
-- :doc create a new user record
INSERT INTO user
(email, name, password)
VALUES (:email, :name, :password)

-- :name get-user-by-id :? :1
-- :doc get user
SELECT * FROM user
WHERE id = :user_id

-- :name get-user-by-email :? :1
-- :doc get user
SELECT * FROM user
WHERE email = :email

-- :name update-user! :! :n
-- :doc update user
UPDATE user
SET name = :name, password = :password
WHERE id = :id

-- :name delete-user! :! :n
-- :doc delete user
DELETE FROM user
WHERE id = :id