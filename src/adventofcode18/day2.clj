(ns adventofcode18.day2)

(def day2-input
  (with-open [rdr (clojure.java.io/reader "resources/day2")]
    (->> (line-seq rdr)
         (vec))))

(defn contains-n [s n]
  {:pre [(pos? n)]}
  (pos? (count (filter #(= n %) (map val (frequencies s))))))

(defn count-contains [input n]
  (count (filter #(contains-n % n) input)))

(defn day2-pt1 [input] 
  (* (count-contains input 2) (count-contains input 3)))

(defn off-by-one [a b] 
  (= 1 (count (filter not (map #'= a b)))))

(defn day2-pt2 [input]
  (->> (first (for [x input
                    y input
                    :when (off-by-one x y)] [x y]))
       (apply map list)
       (filter (fn [[x y]] (= x y)))
       (map first)
       (apply str)))
