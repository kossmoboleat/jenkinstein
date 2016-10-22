(ns jenkinstein.business.files
  (:require [clojure.java.io :as io]
            [ring.util.http-response :refer [ok]]))

(defn list-files []
  (let [curr-dir (io/file "resources/sounds/")]
    (map #(.getName %) (filter #(.isFile %) (file-seq curr-dir)))
    (ok)))