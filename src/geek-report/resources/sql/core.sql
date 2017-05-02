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
