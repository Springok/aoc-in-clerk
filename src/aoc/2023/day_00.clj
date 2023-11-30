; [Day 1 - Advent of Code 2022](https://adventofcode.com/2022/day/1)

; ## Code
(ns aoc.2023.day-00
  {:nextjournal.clerk/visibility {:result :hide}}
  (:require
   [aoc.util :as util]
   [clojure.string :as str]
   [clojure.test :refer [deftest is]]))

(def example
  [[1000 2000 3000] [4000] [5000 6000] [7000 8000 9000] [10000]])

(def input
  (->> (util/read-chunks "../resources/aoc/2022/day1.txt")
       (map str/split-lines)
       (map util/strs->integers)))

(defn part1 [records]
  (->> records
       (map #(apply + %))
       (apply max)))

(defn part2 [records]
  (->> records
       (map #(apply + %))
       (sort >)
       (take 3)
       (apply +)))

(deftest test-example
  (is (= 24000 (part1 example)))
  (is (= 45000 (part2 example))))

; ## Results
{:nextjournal.clerk/visibility {:result :show}}
(part1 example)
(part2 example)


(time (part1 input)) ; Elapsed time: 0.463584 msecs
(time (part2 input)) ; Elapsed time: 0.435542 msecs
