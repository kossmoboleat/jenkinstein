(ns jenkinstein.business.speech
  (:import
    (com.sun.speech.freetts Voice VoiceManager)))

(defn speak [text]
      (let [manager (VoiceManager/getInstance)
            voice (.getVoice manager "kevin")]
        (.allocate voice)
        (.speak voice text)))