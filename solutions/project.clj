(defproject solutions "0.1.0-SNAPSHOT"
  :description "Solutions to the 2019 Advent of Code"
  :url "https://adventofcode.com"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]]
  :main ^:skip-aot day1.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
