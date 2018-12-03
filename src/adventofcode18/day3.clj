(ns adventofcode18.day3)

(def day3-input
  (with-open [rdr (clojure.java.io/reader "resources/day3")]
    (->> (line-seq rdr)
         (map parse-line)
         (vec))))

(defn parse-line [s]
  (let [[id x y w h] (re-seq #"[0-9]+" s)]
    [(Integer. id) 
     (Integer. x) (Integer. y) (Integer. w) (Integer. h)]))

(defn squares [[id x y w h]]
  (into {} (for [i (range x (+ x w))
                 j (range y (+ y h))]
             [[i j] #{id}])))

(defn squares-with-more-than-one-id [input]
  (filter #(> (count %) 1) 
          (vals (apply merge-with clojure.set/union 
                       (map squares day3-input)))) )

(defn day3-pt1 [input]
  (count (squares-with-more-than-one-id day3-input)))

(defn day3-pt2 [input]
  (first (clojure.set/difference 
            (into #{} (map (fn [[id _ _ _ _]] id) day3-input))
            (apply clojure.set/union
                   (squares-with-more-than-one-id day3-input)))))
