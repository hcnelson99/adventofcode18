(ns adventofcode18.day4)

(defn parse-line [line]
  (let [[year month day hour minute id]
        (map #(Integer. %) (re-seq #"\d+" line))]
    (merge {:time [year month day hour minute]}
           {:event (cond id :start
                         (.contains line "wakes") :wake
                         :else :sleep)}
           (if id {:id id} {}))))

(def day4-input
  (with-open [rdr (clojure.java.io/reader "resources/day4")]
    (->> (line-seq rdr)
         (map parse-line)
         (sort-by :time)
         (vec))))

(def x (first (partition-by :id (add-ids day4-input))))

(defn get-minute [event]
  (let [[_ _ _ _ minute] (:time event)]
    minute))

(defn minutes-asleep [night]
  {(:id (first night)) 
   (->> night
        (rest)
        (map get-minute)
        (partition 2)
        (map #(apply range %))
        (map #(into {} (map (fn [x] [x 1]) %)))
        (apply merge-with +)) })

(defn add-ids [input]
  (loop [input input last-id nil acc []]
    (if (empty? input)
      acc
      (let [x (first input)
            id (:id x)]
        (if id
          (recur (rest input) id (conj acc x))
          (recur (rest input) last-id (conj acc (assoc x :id last-id)))))
      )))

(defn select-pt1 [x]
  (apply max-key #(reduce + (vals (second %))) x))

(defn select-pt2 [x]
  (apply max-key #(reduce max (or (vals (second %)) '(0))) x))

(defn solve [input select-fn]
  (let [[id times]
        (->> input
             (add-ids)
             (partition-by :id)
             (map minutes-asleep)
             (apply merge-with (fn [x y] (merge-with + x y)))
             (select-fn))]
    (* id (first (apply max-key #(second %) times)))))

(solve day4-input select-pt1)
(solve day4-input select-pt2)
