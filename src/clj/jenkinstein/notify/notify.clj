(ns jenkinstein.notify.notify
  (:require [clj-http.client :as client]))

(defn notify [{:keys [params]}]
  (println "someone notified us that a job completed")
  (let [url (str (:url params) "api/json?pretty=true")]
    (println url)
    (println (client/get url))
    {}))