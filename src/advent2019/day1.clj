(ns advent2019.day1)

(require '[clojure.java.io :as io])

(defn process-file [filename]
  (prn (str "Filename: " filename))
  (let [fuel-one #(- (quot % 3) 2)
        total-fuel #(->> (iterate fuel-one %)
                         (take-while pos?)
                         (rest)
                         (reduce +))]
    (with-open [reader (io/reader (io/resource filename))]
      (->> (line-seq reader)
           (map read-string)
           (map total-fuel)
           (reduce +)))))

(defn -main
  "On the first day, do the thing with the fuel"
  []
  (prn (process-file "day1.txt")))
