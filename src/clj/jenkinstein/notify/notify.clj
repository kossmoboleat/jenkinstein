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

(defn get-job-json [url]
  (let [json-url (str url "api/json")
        response (client/get url {:as :json})]
    (:body response)))

(defn job-result [response]
  (make-status (:result response)))

(defn get-previous-result [url]
  (let [build-number-str (second (re-find #"/(\d*)/?$"))
        build-number (Integer/parseInt build-number-str)
        previous-build-number (if (= build-number 1)
                                1
                                (dec build-number))
        previous-url (clojure.string/replace url build-number-str (str previous-build-number))]
    (job-result (get-job-json previous-url))))

(defn status-changed? [old-status new-status]
  (not= old-status new-status))

(defn notify [{:keys [params]}]
  (let [url (:url params)
        job_name (parse-job-name url)
        response (get-job-json url)
        old_job_result (get-previous-result url)
        job_result (job-result response)
        culprits (:culprits response)]
    (log/info "Job" job_name "ended with status" job_result "url: " url)
    (let [sound (db/get-sound-by-job-name {:job_name job_name})]
      (when sound
        (let [sound-threshold (make-status (sound :threshold))]
          (when (and (>=-threshold job_result sound-threshold)
                     (status-changed? job-result (get-previous-result url)))
            (do
              (playback/play (sound :sound_filename))
              (handle-failure job_name culprits))))))
    (ok)))


