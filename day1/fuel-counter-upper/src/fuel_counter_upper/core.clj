(ns fuel-counter-upper.core
  (:gen-class))

(require '[clojure.java.io :as io])

(defn process-file [filename]
  (with-open [rdr (io/reader (io/file filename))]
    (->> (line-seq rdr)
         (map read-string)
         (map #(- (quot % 3) 2))
         (reduce +))))

(defn -main
  "On the first day, do the thing with the fuel"
  [& args]
  (prn process-file "input"))
