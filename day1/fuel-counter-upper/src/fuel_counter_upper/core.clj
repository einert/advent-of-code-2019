(ns fuel-counter-upper.core
  (:gen-class))

(require '[clojure.java.io :as io])

(defn process-file-with [line-func line-acc filename]
  (with-open [rdr (io/reader (io/file filename))]
    (reduce line-func line-acc 
            (map #(- (quot % 3) 2) 
                 (map read-string 
                      (line-seq rdr))))))

;;(require '[clojure.string :as string])

(defn accumulate [acc fuel]
  (+ acc fuel))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (prn process-file-with accumulate 0 "input"))
