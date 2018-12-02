(ns adventofcode18.day1)

(defn add-all-nums [nums]
  (reduce + nums))

(def day1-input
  (with-open [rdr (clojure.java.io/reader "resources/day1")]
    (->> (line-seq rdr)
         (map #(Integer. %))
         (vec))))

(defn day1-pt1 [input] 
  (add-all-nums input))

(defn first-dupe [coll]
  (loop [coll coll
         seen #{}]
    (let [x (first coll)]
      (cond (empty? coll) nil
            (seen x) x
            :else (recur (rest coll) (conj seen x))))))


(defn day1-pt2 [input]
  (first-dupe (cons 0 (reductions + (cycle input)))))

(day1-pt1 day1-input)
(day1-pt2 day1-input)
