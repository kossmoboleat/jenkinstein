(ns jenkinstein.business.talk
  (:require [ring.util.http-response :refer [ok]]
            [clojure.tools.logging :as log])
  (:import
    (marytts LocalMaryInterface)
    (javax.sound.sampled AudioSystem)
    (java.util Locale)))

(defn talk [text]
  (let [mary (new LocalMaryInterface)
        clip (AudioSystem/getClip)]
    (log/info "talking" text)
    (.setLocale mary (Locale. "en" "GB"))
    (.setVoice mary "dfki-spike-hsmm")

    (.open clip (.generateAudio mary text))
    (.start clip)
    (ok)))