(ns jenkinstein.business.speech
  (:require [ring.util.http-response :refer [ok]])
  (:import
    (com.sun.speech.freetts Voice VoiceManager)))

(defn speak [text]
      (let [manager (VoiceManager/getInstance)
            voice (.getVoice manager "kevin")]
        (.allocate voice)
        (.speak voice text)
        (ok)))