(ns fuel-counter-upper.core
  (:gen-class))

(require '[clojure.java.io :as io])

(defn total-fuel [module-weight]
  (loop [weight module-weight fuel 0]
    (let [delta (- (quot weight 3) 2)]
      (if (<= delta 0)
        fuel
        (recur delta (+ fuel delta))))))

(defn process-file [filename]
  (with-open [rdr (io/reader (io/file filename))]
    (->> (line-seq rdr)
         (map read-string)
         (map total-fuel)
         (reduce +))))

(defn -main
  "On the first day, do the thing with the fuel"
  [& args]
  (prn (process-file "input")))
