(ns jenkinstein.notify.notify
  (:require [clj-http.client :as client]
            [jenkinstein.business.talk :as talk]
            [clojure.string :refer [join]]))

(defn handle-failure [job-name culprits]
  (println "a job failed!")
  (let [culprit-names (join ", " (map :fullName culprits))]
    (talk/talk (str "The job " job-name " failed since " culprit-names " committed."))))

(defn parse-job-name [url]
  (second (re-find #"/job/(.*?)/" url)))

(defn notify [{:keys [params]}]
  (let [url (str (:url params) "api/json?pretty=true")
        job-name (parse-job-name url)
        response (client/get url {:as :json})
        body (:body response)
        result (:result body)
        culprits (:culprits body)]
    (println "This job completed: " url)
    (println body)
    (if (= result "FAILURE")
      (handle-failure job-name culprits)
      (println "job status was: " result))
    {}))
