(ns jenkinstein.sound.sound
  (:require [jenkinstein.db.core :as db]
            [ring.util.http-response :refer [ok created]]))

(defn create-sound! [{:keys [params]}]
  (let [created-id (db/create-sound! params)
        created-url (str "/sounds/" created-id)]
    (created created-url)))

(defn get-sounds []
  (ok (db/get-sounds)))

(defn get-sound [id]
  (ok (db/get-sound {:id id})))

(defn delete-sound! [id]
  (db/delete-sound! {:id id})
  (ok))