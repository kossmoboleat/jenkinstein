(ns jenkinstein.routes.home
  (:require [jenkinstein.layout :as layout]
            [jenkinstein.business.files :as files]
            [jenkinstein.business.playback :as playback]
            [jenkinstein.business.speech :as speech]
            [jenkinstein.business.talk :as talk]
            [jenkinstein.notify.notify :as notify]
            [jenkinstein.sound.sound :as sound]
            [compojure.core :refer [defroutes GET POST DELETE]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]))

(defn home-page []
  (layout/render
    "home.html" {:docs (-> "docs/docs.md" io/resource slurp)}))

(defn sound-files-page []
  (layout/render "sound-files.html" (:body (files/list-files))))

(defn sounds-page []
  (layout/render "sound-entries.html" {:sounds (:body (sound/get-sounds))}))

(defroutes home-routes
           (GET "/" [] (home-page))
           (GET "/about" [] (layout/render "about.html"))
           (GET "/upload" [] (layout/render "upload.html"))
           (POST "/files" [file] (files/upload-file file))
           (GET "/files" [] (files/list-files))
           (GET "/sound-files" [] (sound-files-page))
           (GET "/sound-entries" [] (sounds-page))
           (GET "/play/:file" [file] (playback/play file))
           (GET "/speak/:text" [text] (speech/speak text))
           (GET "/talk/:text" [text] (talk/talk text))
           (POST "/notify" request (notify/notify request))
           (GET "/sounds" [] (sound/get-sounds))
           (GET "/sounds/:id" [id] (sound/get-sound id))
           (DELETE "/sounds/:id" [id] (sound/delete-sound! id))
           (POST "/sounds" request (sound/create-sound! request)))