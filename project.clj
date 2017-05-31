(defproject pretty-csv "0.1.0-SNAPSHOT"
  :description "Pretty Print CSV Files"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/data.csv "0.1.4"]]
  :main ^:skip-aot pretty-csv.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
