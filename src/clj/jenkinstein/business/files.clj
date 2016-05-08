(ns jenkinstein.business.files
  (:require [clojure.java.io :as io]))

(defn list-files []
  (let [curr-dir (io/file "resources/sounds/")]
    (map #(.getName %) (filter #(.isFile %) (file-seq curr-dir)))))