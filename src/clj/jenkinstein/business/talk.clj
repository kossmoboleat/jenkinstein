(ns jenkinstein.business.talk
  (:import
    (marytts LocalMaryInterface)
    (marytts.exceptions MaryConfigurationException SynthesisException)
    (marytts.util.data.audio MaryAudioUtils)
    (javax.sound.sampled AudioSystem)
    (java.util Locale)))

(defn talk [text]
  (let [mary (new LocalMaryInterface)
        clip (AudioSystem/getClip)]
    (.setVoice mary "dfki-spike")

    (.open clip (.generateAudio mary text))
    (.start clip)
    {}))