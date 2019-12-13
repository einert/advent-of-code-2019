(ns fuel-counter-upper.core
  (:gen-class))

(require '[clojure.java.io :as io])

(defn process-file [filename]
  (let [fuel-one #(- (quot % 3) 2)
        total-fuel #(->> (iterate fuel-one %)
                         (take-while pos?)
                         (rest)
                         (reduce +))]
    (with-open [reader (io/reader (io/file filename))]
      (->> (line-seq reader)
           (map read-string)
           (map total-fuel)
           (reduce +)))))

(defn -main
  "On the first day, do the thing with the fuel"
  [& args]
  (prn (process-file "input")))
