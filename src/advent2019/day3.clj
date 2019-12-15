(ns advent2019.day3
  (:require
   [clojure.string :as str]
   [clojure.set :as hset]))

;; again, fdside provides the inspiration and basis for the coe

(def input
  (->> (slurp "resources/day3.txt")
       str/split-lines
       (map #(str/split % #","))))

(defn calc-wire-points [[x y] dir dist]
  (case dir
    \R (for [xm (take dist (iterate inc (inc x)))] [xm y])
    \L (for [xm (take dist (iterate dec (dec x)))] [xm y])
    \U (for [ym (take dist (iterate inc (inc y)))] [x ym])
    \D (for [ym (take dist (iterate dec (dec y)))] [x ym])))

(defn extend-wire [locations [dir & dist]]
  (concat locations 
          (calc-wire-points
           (last locations)
           dir
           (read-string (apply str dist)))))

(defn create-wire [inp]
  (reduce extend-wire [[0 0]] inp))

(def wire1 (create-wire (first input)))
(def wire2 (create-wire (second input)))
(def intersections (hset/intersection (set wire1) (set wire2)))

(defn abs [n] (max n (- n)))

(defn closest-intersection []
  (->> intersections
       (map #(map abs %))
       (map #(reduce + %))
       sort
       second)) ; origo doesn't count

(defn shortest-intersection []
  (->> intersections
       (map #(+ (.indexOf wire1 %) (.indexOf wire2 %)))
       sort
       second))