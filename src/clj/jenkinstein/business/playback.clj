(ns jenkinstein.business.playback
  (:require [clojure.java.io :as io])
  (:import
    (javax.sound.sampled AudioSystem LineEvent$Type LineListener)
    (java.net URL)))

(defn play-file
  [file]
  (let [stream (AudioSystem/getAudioInputStream file)
        clip   (AudioSystem/getClip)]
    (.addLineListener clip
                      (proxy[LineListener] []
                        (update [event]
                          (when (and event
                                     (= (.getType event)
                                        (LineEvent$Type/STOP)))

                            (.close clip)
                            (.close stream)))))
    (try
      (.open clip stream)
      (.start clip)
      (catch Exception e nil))))


(defn play [filename]
  (play-file (io/file (str "resources/sounds/" filename))))
