(ns advent2019.day7
  (:require 
   [clojure.string :as string]
   ;[clojure.math.combinatorics :as combo]
   [clojure.test :as t]))

;; Mostly stolen from fdside's awesome implementation
;; https://github.com/fdside/advent-of-code-2019-clj/blob/master/src/advent_of_code_2019/day5.clj

(def input (vec (map
                 read-string
                 (string/split (slurp "resources/day7.txt") #","))))

; (defn digits [n]
;   (->> n str (map (comp read-string str))))

(defn digits [n]
  (->> n
       (iterate #(quot % 10))
       (take-while pos?)
       (mapv #(mod % 10))
       rseq))

(t/with-test

  (defn execute
    ([program & inputs]
     (loop [memory program
            i 0
            input-num inputs
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
        ;  (println (str "i" i "input-num " input-num "op" op "a" a "b" b))
        ;  (flush)
         (case (mod op 100)
           1 (recur (assoc memory target (+ a b)) (+ i 4) input-num result) ; add
           2 (recur (assoc memory target (* a b)) (+ i 4) input-num result) ; mult
           3 (recur (assoc memory (get memory (inc i)) (first input-num)) (+ i 2) (rest input-num) result) ; get input
           4 (recur memory (+ i 2) input-num (conj result a)) ; write output
           5 (recur memory (if (not= 0 a) b (+ i 3)) input-num result) ; jump-if-true
           6 (recur memory (if (= 0 a) b (+ i 3)) input-num result) ; jump-if-false
           7 (recur (assoc memory target (if (< a b) 1 0)) (+ i 4) input-num result) ; less-than
           8 (recur (assoc memory target (if (= a b) 1 0)) (+ i 4) input-num result) ; equals
           99 [memory (last result)]))))) ; quit
  
  ;; test-o-rama
  (t/is (= [2 0 0 0 99] (first (execute [1 0 0 0 99]))))
  (t/is (= [2 3 0 6 99] (first (execute [2 3 0 3 99]))))
  (t/is (= [2 4 4 5 99 9801] (first (execute [2 4 4 5 99 0]))))
  (t/is (= [30 1 1 4 2 5 6 0 99] (first (execute [1 1 1 4 99 5 6 0 99]))))
  (t/is (= [1002 4 3 4 99]) (first (execute [1002 4 3 4 33])))
  (t/is (= [1101 100 -1 4 -100]) (first (execute [1101 100 -1 4 0])))
  (t/is (= 0) (second (execute [3 9 8 9 10 9 4 9 99 -1 8] 7)))
  (t/is (= 1) (second (execute [3 9 8 9 10 9 4 9 99 -1 8] 8)))
  (t/is (= 0) (second (execute [3 9 7 9 10 9 4 9 99 -1 8] 9)))
  (t/is (= 1) (second (execute [3 9 7 9 10 9 4 9 99 -1 8] 7)))
  (t/is (= 0) (second (execute [3 3 1108 -1 8 3 4 3 99] 7)))
  (t/is (= 1) (second (execute [3 3 1108 -1 8 3 4 3 99] 8)))
  (t/is (= 0) (second (execute [3 3 1107 -1 8 3 4 3 99] 9)))
  (t/is (= 1) (second (execute [3 3 1107 -1 8 3 4 3 99] 7)))
  (t/is (= 0) (second (execute [3 12 6 12 15 1 13 14 13 4 13 99 -1 0 1 9] 0)))
  (t/is (= 1) (second (execute [3 12 6 12 15 1 13 14 13 4 13 99 -1 0 1 9] 42)))
  (t/is (= 0) (second (execute [3 3 1105 -1 9 1101 0 0 12 4 12 99 1] 0)))
  (t/is (= 1) (second (execute [3 3 1105 -1 9 1101 0 0 12 4 12 99 1] 42)))
  (t/is (= 1) (second (execute [3 15 3 16 1002 16 10 16 1 16 15 15 4 15 99 0 0] 42 0)))
  

  
  (t/is (= 999)  (second (execute [3 21 1008 21 8 20 1005 20 22 107 8
                                   21 20 1006 20 31 1106 0 36 98 0 0
                                   1002 21 125 20 4 20 1105 1 46 104
                                   999 1105 1 46 1101 1000 1 20 4 20
                                   1105 1 46 98 99] 7)))
  (t/is (= 1000) (second (execute [3 21 1008 21 8 20 1005 20 22 107 8
                                   21 20 1006 20 31 1106 0 36 98 0 0
                                   1002 21 125 20 4 20 1105 1 46 104
                                   999 1105 1 46 1101 1000 1 20 4 20
                                   1105 1 46 98 99] 8)))
  (t/is (= 1001) (second (execute [3 21 1008 21 8 20 1005 20 22 107 8
                                   21 20 1006 20 31 1106 0 36 98 0 0
                                   1002 21 125 20 4 20 1105 1 46 104
                                   999 1105 1 46 1101 1000 1 20 4 20
                                   1105 1 46 98 99] 9))))

; day 7
()

;;

(defn permutations [s]
  (lazy-seq
   (if (seq (rest s))
     (apply concat (for [x s]
                     (map #(cons x %) (permutations (remove #{x} s)))))
     [s])))

(t/with-test
  
  (defn serial [program phase-seq signal]
    (if (empty? phase-seq)
      signal
      (recur program (rest phase-seq) 
             (second (execute program (first phase-seq) signal)))))
  
  (t/is (= 43210) (serial [3 15 3 16 1002 16 10 16 1 16 15 15 4 15 99 0 0] (digits 43210) 0))
  (t/is (= 54321) (serial [3 23 3 24 1002 24 10 24 1002 23 -1 23 101 5 23 23 1 24 23 23 4 23 99 0 0] (digits 01234) 0))
  (t/is (= 65210) (serial [3 31 3 32 1002 32 10 32 1001 31 -2 31 1007 31 0 33 1002 33 7 33 1 33 31 31 1 32 31 31 4 31 99 0 0 0] (digits 10432) 0))

  )

(defn part1 []
  (reduce max
          (for [phase-seq (permutations '(0 1 2 3 4))]
            (serial input phase-seq 0))))

