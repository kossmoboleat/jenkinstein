(defproject jenkinstein "0.1.0-SNAPSHOT"

  :description "jenkinstein"
  :url "https://github.com/kossmoboleat/jenkinstein"

  :dependencies [[luminus-log4j "0.1.3"]
                 [org.clojure/clojure "1.8.0"]
                 [selmer "1.0.4"]
                 [markdown-clj "0.9.89"]
                 [ring-middleware-format "0.7.0"]
                 [metosin/ring-http-response "0.8.0"]
                 [bouncer "1.0.0"]
                 [org.webjars/bootstrap "4.0.0-alpha.3"]
                 [org.webjars/font-awesome "4.6.3"]
                 [org.webjars.bower/tether "1.3.7"]
                 [org.webjars/jquery "3.0.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [compojure "1.5.1"]
                 [ring-webjars "0.1.1"]
                 [ring/ring-defaults "0.2.1"]
                 [mount "0.1.10"]
                 [cprop "0.1.9"]
                 [org.clojure/tools.cli "0.3.5"]
                 [luminus-nrepl "0.1.4"]
                 [luminus-migrations "0.2.7"]
                 [conman "0.6.1"]
                 [com.h2database/h2 "1.4.192"]
                 [org.webjars/webjars-locator-jboss-vfs "0.1.0"]
                 [luminus-immutant "0.2.2"]
                 [clj-http "2.3.0"]

                 [org.mobicents.external.freetts/cmu_time_awb "1.0"]
                 [org.mobicents.external.freetts/cmudict04 "1.0"]
                 [org.mobicents.external.freetts/cmulex "1.0"]
                 [org.mobicents.external.freetts/cmutimelex "1.0"]
                 [org.mobicents.external.freetts/cmu_us_kal "1.0"]
                 [org.mobicents.external.freetts/en_us "1.0"]
                 [org.mobicents.external.freetts/freetts "1.0"]
                 [de.dfki.mary/voice-dfki-spike-hsmm "5.2"]]

  :mirrors      {"central" {:url "https://jcenter.bintray.com"}}

  :repositories {"maven"   {:url "https://repo1.maven.org/maven2/"}}

  :min-lein-version "2.0.0"

  :jvm-opts ["-server" "-Dconf=.lein-env"]
  :source-paths ["src/clj"]
  :resource-paths ["resources"]

  :main jenkinstein.core

  :migratus {:store :database :db ~(get (System/getenv) "DATABASE_URL")}

  :plugins [[lein-cprop "1.0.1"]
            [migratus-lein "0.4.2"]
            [lein-immutant "2.1.0"]]

  :target-path "target/%s/"
  :profiles
  {:uberjar       {:omit-source    true

                   :aot            :all
                   :uberjar-name   "jenkinstein.jar"
                   :source-paths   ["env/prod/clj"]
                   :resource-paths ["env/prod/resources"]}

   :dev           [:project/dev :profiles/dev]
   :test          [:project/dev :project/test :profiles/test]
   :project/dev   {:dependencies   [[prone "1.1.2"]
                                    [ring/ring-mock "0.3.0"]
                                    [ring/ring-devel "1.5.0"]
                                    [pjstadig/humane-test-output "0.8.1"]]
                   :plugins        [[com.jakemccrary/lein-test-refresh "0.14.0"]
                                    [lein-localrepo "0.5.3"]]


                   :source-paths   ["env/dev/clj" "test/clj"]
                   :resource-paths ["env/dev/resources"]
                   :repl-options   {:init-ns user}
                   :injections     [(require 'pjstadig.humane-test-output)
                                    (pjstadig.humane-test-output/activate!)]}
   :project/test  {:resource-paths ["env/dev/resources" "env/test/resources"]}
   :profiles/dev  {:log-level :trace}
   :profiles/test {}})
