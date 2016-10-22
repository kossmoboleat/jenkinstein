-- :name create-sound! :! :n
-- :doc creates a new sound record
INSERT INTO sounds
(id, job_name_regex, sound_filename, threshold)
VALUES (:id, :job_name_regex, :sound_filename, :threshold);

-- :name get-sounds :? :*
-- :doc retrieve all sounds
SELECT *
FROM sounds;

-- :name get-sound :? :1
-- :doc retrieve a sound given the id.
SELECT *
FROM sounds
WHERE id = :id;

-- :name delete-sound! :! :n
-- :doc delete a sound given the id
DELETE FROM sounds
WHERE id = :id;

-- :name get-sound-by-job-name :? :1
-- :doc retrieve a sound given a job-name.
SELECT *
FROM sounds
WHERE job_name_regex = :job_name;