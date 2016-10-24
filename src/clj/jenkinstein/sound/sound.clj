(ns jenkinstein.sound.sound
  (:require [jenkinstein.db.core :as db]
            [ring.util.http-response :refer [ok created]]
            [ring.util.response :refer [redirect]]))

;(defn create-sound! [{:keys [params]}]
;  (let [created-id (db/create-sound! params)
;        created-url (str "/sounds/" created-id)]
;    (created created-url)))

(defn create-sound! [{:keys [params]}]
  (let [max_id_str (:max_id (db/get-max-sound-id))
        max_id (Integer/parseInt (if (nil? max_id_str) "0" max_id_str))]
    (db/create-sound! (assoc params :id (inc max_id)))
    (redirect "/sound-entries")))

(defn get-sounds []
  (ok (db/get-sounds)))

(defn get-sound [id]
  (ok (db/get-sound {:id id})))

(defn delete-sound! [id]
  (db/delete-sound! {:id id})
  (ok))
