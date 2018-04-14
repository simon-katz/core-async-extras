(defproject nomis-async "0.1.1-SNAPSHOT"
  :description "Useful things atop core.async"
  :url "https://github.com/simon-katz/nomis-async"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/core.async "0.4.474"]]
  :profiles {:dev {:source-paths ["dev"]
                   :dependencies [[org.clojure/tools.namespace "0.2.11"]
                                  [midje "1.9.1"]
                                  [clj-time "0.14.3"]]
                   :plugins [[lein-midje "3.2.1"]]}})
