-- noinspection SqlNoDataSourceInspectionForFile

-- :name create-sound! :! :n
-- :doc creates a new sound record
INSERT INTO sounds
(id, job_name, sound_filename, threshold)
VALUES (:id, :job_name, :sound_filename, :threshold);

-- :name get-sounds :? :*
-- :doc retrieve all sounds
SELECT *
FROM sounds;

-- :name get-sound :? :1
-- :doc retrieve a sound given the id.
SELECT *
FROM sounds
WHERE id = :id;

-- :name get-max-sound-id :? :1
-- :doc get the maximum sound id.
SELECT MAX(id) AS MAX_ID
FROM sounds;

-- :name delete-sound! :! :n
-- :doc delete a sound given the id
DELETE FROM sounds
WHERE id = :id;

-- :name get-sound-by-job-name :? :1
-- :doc retrieve a sound given a job-name.
SELECT *
FROM sounds
WHERE job_name = :job_name;