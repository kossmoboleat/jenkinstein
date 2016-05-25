(ns jenkinstein.business.talk
  (:import
    (marytts LocalMaryInterface)
    (marytts.exceptions MaryConfigurationException SynthesisException)
    (marytts.util.data.audio MaryAudioUtils)
    (javax.sound.sampled AudioSystem)))

(defn talk [text]
  (let [mary (new LocalMaryInterface)
        clip (AudioSystem/getClip)
        stream (.generateAudio mary text)]
    (.open clip stream)
    (.start clip)
    "done"))