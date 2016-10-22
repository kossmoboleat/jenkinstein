(ns jenkinstein.notify.notify
  (:require [clj-http.client :as client]
            [jenkinstein.business.talk :as talk]
            [jenkinstein.business.playback :as playback]
            [jenkinstein.db.core :as db]
            [clojure.string :refer [join]]
            [clojure.java.jdbc :as jdbc]
            [ring.util.http-response :refer [ok created]]))

(defn handle-failure [job_name culprits]
  (println "a job failed!")
  (let [culprit-names (join ", " (map :fullName culprits))]
    (talk/talk (str "The job " job_name " failed since " culprit-names " committed."))))

(defn parse-job-name [url]
  (second (re-find #"/job/(.*?)/" url)))

(defn is-higher-threshold [result threshold]
  true)


(defn notify [{:keys [params]}]
  (let [url (str (:url params) "api/json?pretty=true")
        job_name (parse-job-name url)
        response (client/get url {:as :json})
        body (:body response)
        result (:result body)
        culprits (:culprits body)]
    (println "This job completed: " url)
    (println body)
    (println "job status was: " result)
    (let [sound (db/get-sound-by-job-name {:job_name job_name})]
      (if (and sound (is-higher-threshold result (sound :threshold)))
        (playback/play (sound :sound_filename))))
    (if (= result "FAILURE")
      (handle-failure job_name culprits))
    (ok)))

(defn register-sound [{:keys [params]}]
  (println params)
  (let [created-id (db/create-sound! params)
        created-url (str "/sounds/" created-id)]
    (created created-url)))

(defn get-sounds []
  (ok (db/get-sounds)))

(defn get-sound [id]
  (ok (db/get-sound {:id id})))

(defn delete-sound [id]
  (ok (db/delete-sound! {:id id})))