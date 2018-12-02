(ns adventofcode18.day2-test
  (:require [clojure.test :refer :all]
            [adventofcode18.day2 :refer :all]))

(deftest test-contains-n
  (is (contains-n "bababc" 2))
  (is (contains-n "bababc" 3))
  (is (not (contains-n "aabcdd" 3)))
  (is (contains-n "aabcdd" 2))
  (is (thrown? AssertionError (contains-n "aabcdd" 0))))

(def test-pt1-input ["abcdef" "bababc" "abbcde" "abcccd" "aabcdd" "abcdee" "ababab"])

(deftest test-count-contains
  (is (= 4 (count-contains test-pt1-input 2)))
  (is (= 3 (count-contains test-pt1-input 3))))

(deftest test-pt1
  (is (= 12 (day2-pt1 test-pt1-input)))
  (is (= 5727 (day2-pt1 day2-input))))

(deftest test-off-by-one
  (is (off-by-one "fghij" "fguij")))

(def test-pt2-input ["abcde" "fghij" "klmno" "pqrst" "fguij" "axcye" "wvxyz"])

(deftest test-pt2
  (is (= "fgij" (day2-pt2 test-pt2-input)))
  (is (= "uwfmdjxyxlbgnrotcfpvswaqh" (day2-pt2 day2-input))) )
