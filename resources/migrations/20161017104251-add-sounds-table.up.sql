CREATE TABLE sounds
(
  id             VARCHAR(20) PRIMARY KEY,
  job_name_regex VARCHAR(64)  NOT NULL,
  sound_filename VARCHAR(300) NOT NULL,
  threshold      VARCHAR(10)
);