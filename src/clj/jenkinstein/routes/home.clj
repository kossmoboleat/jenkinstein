(ns jenkinstein.routes.home
  (:require [jenkinstein.layout :as layout]
            [jenkinstein.business.files :as files]
            [jenkinstein.business.playback :as playback]
            [jenkinstein.business.speech :as speech]
            [jenkinstein.business.talk :as talk]
            [jenkinstein.notify.notify :as notify]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]))

(defn home-page []
  (layout/render
    "home.html" {:docs (-> "docs/docs.md" io/resource slurp)}))

(defn about-page []
  (layout/render "about.html"))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page))
  (GET "/list" [] (files/list-files))
  (GET "/play/:file" [file] (playback/play file))
  (GET "/speak/:text" [text] (speech/speak text))
  (GET "/talk/:text" [text] (talk/talk text))
  (POST "/notify" request (notify/notify request)))
