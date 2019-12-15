(ns advent2019.day3
  (:require [clojure.test :as t]
            [clojure.java.io :as io]))

(t/with-test
  
  (defn manhattan-distance
    ([[x y]] (manhattan-distance [x y] [0 0]))
    ([[x y] [x0 y0]] (+ (Math/abs (- x x0)) 
                        (Math/abs (- y y0)))))
  
  (t/is (= 1 (manhattan-distance [1 0])))
  (t/is (= 1 (manhattan-distance [-1 0])))
  (t/is (= 2 (manhattan-distance [1 -1])))
  (t/is (= 30 (manhattan-distance [10 20])))
  )

;; For red and blue wires, convert turtle specs to sorted 
;; collections of *horizontal* and *vertical* line segments.
;; red-wire { :horizontal <sorted-map {level [from to]}> :vertical <sorted-map {level [from to]}>
(t/with-test
  
  (defn segment
    ([path] (segment path [0 0]))
    ([path [x y]]
     (case (path :direction)
       :R {:orientation :horizontal
           :position y 
           :range [x (+ x (path :distance))]}
       :L {:orientation :horizontal
           :position y
           :range [(- x (path :distance)) x]}
       :U {:orientation :vertical
           :position x
           :range [y (+ y (path :distance))]}
       :D {:orientation :vertical
           :position y
           :range [(- y (path :distance)) y]})))
  )

(defn path->lineset 
  [path]
  (map segment path))

;; interesections (X,Y) are given by looking up intersections between 
;; * red horizontals and blue verticals
;; * blue horizontals and red verticals

;; map intersections through manhattan distance, get the smallest one
