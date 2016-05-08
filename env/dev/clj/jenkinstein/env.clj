(ns jenkinstein.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [jenkinstein.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[jenkinstein started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[jenkinstein has shutdown successfully]=-"))
   :middleware wrap-dev})
