(ns jenkinstein.notify.notify
  (:require [clj-http.client :as client]
            [jenkinstein.business.talk :as talk]
            [jenkinstein.business.playback :as playback]
            [jenkinstein.db.core :as db]
            [clojure.string :refer [join]]
            [clojure.tools.logging :as log]
            [ring.util.http-response :refer [ok created]]
            [ring.util.codec :refer [url-decode]]))

(defn handle-failure [job_name culprits]
  (let [culprit-part (when (not (empty? culprits))
                       (str "since " (join ", " (map :fullName culprits)) " committed"))]
    (talk/talk (str "The job " job_name "failed" culprit-part "."))))

(defn parse-job-name [url]
  (url-decode (second (re-find #"job/([^/]*?)/\d*/?$" url))))

(defn >=-threshold [left right]
  (let [values [:success :unstable :failure :not-built :aborted]]
    (>= (.indexOf values left) (.indexOf values right))))

(defn make-status [status-str]
  (keyword (clojure.string/lower-case status-str)))

(defn notify [{:keys [params]}]
  (let [original-url (:url params)
        job_name (parse-job-name original-url)
        url (str original-url "api/json?pretty=true")
        response (client/get url {:as :json})
        body (:body response)
        job_result (make-status (:result body))
        culprits (:culprits body)]
    (log/info "Job" job_name "ended with status" job_result "url: " original-url)
    (let [sound (db/get-sound-by-job-name {:job_name job_name})]
      (when sound
        (let [sound-threshold (make-status (sound :threshold))]
          (when (>=-threshold job_result sound-threshold)
            (do
              (playback/play (sound :sound_filename))
              (if (= job_result "FAILURE")
                (handle-failure job_name culprits)))))))
    (ok)))