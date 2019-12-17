(ns advent2019.day6
  (:require [clojure.test :as t]))

;; Build tree from unsorted input
;; Gut feeling: { <node> [<children] etc. }
;; 2nd pass: Traverse tree depth first to 
;;           sum up distances from root.

(t/with-test

  (defn process-input [input]
    (vec (map #(clojure.string/split % #"\)")
              (clojure.string/split input #"\n"))))

  (def test-input (process-input "COM)B
B)C
C)D
D)E
E)F
B)G
G)H
D)I
E)J
J)K
K)L"))

  (t/is (= test-input
           [["COM" "B"]
            ["B" "C"]
            ["C" "D"]
            ["D" "E"]
            ["E" "F"]
            ["B" "G"]
            ["G" "H"]
            ["D" "I"]
            ["E" "J"]
            ["J" "K"]
            ["K" "L"]])))

  (def input
    (process-input (slurp "resources/day6.txt")))

  (defn put [orbits pair]
    (assoc orbits (first pair) (conj (or (orbits (first pair)) []) (second pair))))

(t/with-test
  (defn build-orbits [input]
    (reduce put {} input))
  
  (t/is (= {"COM" ["B"], 
            "B" ["C" "G"], 
            "C" ["D"], 
            "D" ["E" "I"], 
            "E" ["F" "J"], 
            "G" ["H"], 
            "J" ["K"], 
            "K" ["L"]} 
           (build-orbits test-input)))
  )

  (defn sum-orbits
    "Breadth first scan of orbits tree, sum distances to get #orbits"
    [orbits center level]
    (loop [origin center
           bodies (orbits center)
           distance level
           sum 0]
      (println (str "Bodies " bodies))
      (if (empty? bodies)
        (if (empty? (rest (orbits origin)))
          sum
          (recur origin (rest (orbits origin)) distance sum))
        (recur (first bodies) (orbits (first bodies)) (inc distance)
               (+ sum (* distance (count bodies)))))))
  
      
      ; (if (not-empty bodies)
      ;   (do (println (str "Passing on " (first bodies) " dist " (inc distance)))
      ;       (recur (orbits (first bodies)) (inc distance) (+ sum (* distance (count bodies)))))
      ;   (do (println (str "Recursing " (rest bodies) " distance: " distance " sum: " (+ sum distance)))
      ;       (recur (rest bodies) distance (+ sum distance))))))




  (defn part1 []
    
    (build-orbits (take 10 input)))