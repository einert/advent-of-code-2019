(ns advent2019.day4)

(defn digits [n]
  (->> n str (map (comp read-string str))))

(defn has-repetition? [pin]
  (some? (some #(= (first %) (second %))
               (partition 2 1 (digits pin)))))

(defn is-ascending? [pin]
  (every? #(<= (first %) (second %))
          (partition 2 1 (digits pin))))

(defn viable [code-seq]
  (filter (every-pred is-ascending? has-repetition?) code-seq))

(defn count-viable-in-range []
  (count (viable (range 108457 562042))))

