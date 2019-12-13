(ns advent2019.day4)

(defn digits [n]
  (->> n str (map (comp read-string str))))

(defn has-repetition? [pin]
  (some? (some #(= (first %) (second %))
               (partition 2 1 (digits pin)))))

(defn has-pair? [pin]
  (some? (some #(= (count (first %)) 2) 
               (re-seq #"([0-9])\1+" (str pin)))))

(defn is-ascending? [pin]
  (every? #(<= (first %) (second %))
          (partition 2 1 (digits pin))))

(defn viable-1 [code-seq]
  (filter (every-pred is-ascending? has-repetition?) code-seq))

(defn viable-2 [code-seq]
  (filter (every-pred is-ascending? has-pair?) code-seq))

(defn count-viable-in-range []
  {:loose (count (viable-1 (range 108457 562042)))
   :strict (count (viable-2 (range 108457 562042)))})
