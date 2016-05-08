(ns jenkinstein.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[jenkinstein started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[jenkinstein has shutdown successfully]=-"))
   :middleware identity})
