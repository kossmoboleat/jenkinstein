(ns jenkinstein.business.files
  (:require [clojure.java.io :as io]
            [ring.util.http-response :refer [ok]]
            [ring.util.response :refer [redirect file-response]])
  (:import [java.io File FileInputStream FileOutputStream]))

(def sounds-path "resources/sounds/")

(defn list-files []
  (let [curr-dir (io/file sounds-path)
        files (map #(.getName %) (filter #(.isFile %) (file-seq curr-dir)))]
    (ok {:files (vec files)})))

(defn- file-path [path & [filename]]
  (java.net.URLDecoder/decode
    (str path File/separator filename)
    "utf-8"))

(defn- upload-file-to-path
  "uploads a file to the target folder
   when :create-path? flag is set to true then the target path will be created"
  [path {:keys [tempfile size filename]}]
  (try
    (with-open [in (new FileInputStream tempfile)
                out (new FileOutputStream (file-path path filename))]
      (let [source (.getChannel in)
            dest (.getChannel out)]
        (.transferFrom dest source 0 (.size source))
        (.flush out)))))

(defn upload-file [file]
  (upload-file-to-path sounds-path file)
  (redirect (str "/sound-files")))