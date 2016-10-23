(ns jenkinstein.business.playback
  (:require [clojure.java.io :as io]
            [ring.util.http-response :refer [ok]])
  (:import
    (javax.sound.sampled AudioSystem LineEvent$Type LineListener)
    (java.lang Thread)))

(defn- play-file
  [file]
  (let [stream (AudioSystem/getAudioInputStream file)
        clip (AudioSystem/getClip)
        finished (atom false)]
    (.addLineListener clip
                      (proxy [LineListener] []
                        (update [event]
                          (when (and event
                                     (= (.getType event)
                                        (LineEvent$Type/STOP)))

                            (.close clip)
                            (.close stream)

                            (reset! finished true)))))
    (try
      (.open clip stream)
      (.start clip)
      (catch Exception e nil))
    (while (not @finished)
      (Thread/sleep 100))))


(defn play [filename]
  (play-file (io/file (str "resources/sounds/" filename)))
  (ok))
