(ns advent2019.day5
  (:require [clojure.test :as t]))

;; Mostly stolen from fdside's awesome implementation
;; https://github.com/fdside/advent-of-code-2019-clj/blob/master/src/advent_of_code_2019/day5.clj

(def input (vec (map 
                 read-string 
                 (clojure.string/split (slurp "resources/day5.txt") #","))))

(t/with-test

  (defn execute
    ([program] (execute program nil)) 
    ([program input-num]
     (loop [memory program
            i 0
            result []]
       (let [op (get memory i)
             a (get memory
                    (if (= 1 (mod (quot op 100) 10)) ; mode - immediate or dereference
                      (+ i 1)
                      (get memory (+ i 1))))
             b (get memory
                    (if (= 1 (mod (quot op 1000) 10))
                      (+ i 2)
                      (get memory (+ i 2))))
             target (get memory (+ i 3))]
         (case (mod op 100)
           1 (recur (assoc memory target (+ a b)) (+ i 4) result)
           2 (recur (assoc memory target (* a b)) (+ i 4) result)
           3 (recur (assoc memory (get memory (inc i)) input-num) (+ i 2) result)
           4 (recur memory (+ i 2) (conj result a))
           99 [memory (last result)])))))


  (t/is (= [2 0 0 0 99] (first (execute [1 0 0 0 99]))))
  (t/is (= [2 3 0 6 99] (first (execute [2 3 0 3 99]))))
  (t/is (= [2 4 4 5 99 9801] (first (execute [2 4 4 5 99 0]))))
  (t/is (= [30 1 1 4 2 5 6 0 99] (first (execute [1 1 1 4 99 5 6 0 99]))))
  
  )