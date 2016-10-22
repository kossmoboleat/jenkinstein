(ns user
  (:require [mount.core :as mount]
            jenkinstein.core))

(defn start []
  (mount/start-without #'jenkinstein.core/repl-server))

(defn stop []
  (mount/stop-except #'jenkinstein.core/repl-server))

(defn restart []
  (stop)
  (start))