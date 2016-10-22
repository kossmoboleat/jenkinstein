(ns jenkinstein.business.talk
  (:import
    (marytts LocalMaryInterface)
    (javax.sound.sampled AudioSystem)
    (java.util Locale)))

(defn talk [text]
  (let [mary (new LocalMaryInterface)
        clip (AudioSystem/getClip)]
    (.setLocale mary (Locale. "en" "GB"))
    (.setVoice mary "dfki-spike-hsmm")

    (.open clip (.generateAudio mary text))
    (.start clip)
    {:result "success"}))