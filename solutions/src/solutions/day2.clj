(ns advent2019.day2
  (:require [clojure.test :as t]
            [clojure.java.io :as io]))

;; Solution from elatedpixel was a real eye-opener
;; Borrowed/copied shamelessly from him to play with the
;; code and find out how it works.
;; Simplified away the map, which I don't yet
;; see the use for (but day 2 is not over yet...)

(def input 
  (->> "day2.txt"
       io/resource
       io/reader
       line-seq
       first
       (format "[%s]")
       read-string))

(def opcode 
  {1 +
   2 *})

(t/with-test

  (defn computer
    ([program] (reduce computer program (range)))
    ([program i]
     (let [[op a b r] (nth (partition-all 4 4 program) i)]
       (if (= 99 op)
         (reduced program) ; all done, bail out of reduce loop
         (assoc program r ((opcode op) (program a) (program b)))))))

  (t/is (= [2 0 0 0 99] (computer [1 0 0 0 99])))
  (t/is (= [2 3 0 6 99] (computer [2 3 0 3 99])))
  (t/is (= [2 4 4 5 99 9801] (computer [2 4 4 5 99 0])))
  (t/is (= [30 1 1 4 2 5 6 0 99] (computer [1 1 1 4 99 5 6 0 99])))
  )

(defn part1
  []
  (computer (-> input
                (assoc 1 12)
                (assoc 2 2))))

(defn part2
  []
  (some #(if (= 19690720 (second %)) (first %) )
         (pmap (fn [[a b]]
                 [(+ b (* 100 a))
                  (first (computer (-> input
                                       (assoc 1 a)
                                       (assoc 2 b))))])
               (for [a (range 100) b (range 100)] [a b]))))