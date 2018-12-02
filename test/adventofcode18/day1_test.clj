(ns adventofcode18.day1-test
  (:require [clojure.test :refer :all]
            [adventofcode18.day1 :refer :all]))


(deftest test-add-all-nums
  (is (= 10 (add-all-nums [-11 1 2 3 4 5 6]))))

(deftest test-first-dupe
  (is (= nil (first-dupe [0 1])))
  (is (= 0 (first-dupe [0 1 0])))
  (is (= 2 (first-dupe [0 1 2 3 4 5 2 4]))) )

(deftest test-day1-pt2
  (is (= 0 (day1-pt2 [1 -1])))
  (is (= 10 (day1-pt2 [3 3 4 -2 -4])))
  (is (= 5 (day1-pt2 [-6 3 8 5 -6])))
  (is (= 14 (day1-pt2 [7 7 -2 -7 -4]))))

