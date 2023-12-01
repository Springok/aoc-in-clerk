;; # 🎄 Advent of Code: 2023 Day 1: Trebuchet?!
(ns aoc.2023.day-01
  (:require
   [nextjournal.clerk :as clerk]
   [aoc.util :as util]
   [clojure.string :as str]
   [clojure.test :refer [deftest is]]))

{:nextjournal.clerk/visibility {:result :hide}}

;; ## Example Data / Input / Mapping

(def example
  ["1abc2" "pqr3stu8vwx" "a1b2c3d4e5f" "treb7uchet"])

(def example-2
  ["two1nine"
   "eightwothree"
   "abcone2threexyz"
   "xtwone3four"
   "4nineeightseven2"
   "zoneight234"
   "7pqrstsixteen"])

(def input
  (util/read-file-by-line "../resources/aoc/2023/day1.txt"))

(def mapping
  (let [numbering ["one" "two" "three" "four" "five" "six" "seven" "eight" "nine"]]
    (zipmap (flatten [numbering (map str/reverse numbering)]) (flatten (repeat 2 (range 1 (inc 9)))))))

(def rule-1
  #"\d{1}")

(def rule-2
 #"\d{1}|one|two|three|four|five|six|seven|eight|nine")

(def rule-3
 #"\d{1}|eno|owt|eerht|ruof|evif|xis|neves|thgie|enin")

;; 第一題比較容易，直接用 regexp 的 `d{1}` 就好了，搭配 `re-seq` 基本上就可以把所有數字找到了，然後取頭取尾

(defn part1 [records]
  (->> records
       (map #(re-seq rule-1 %))
       (map #(str (first %) (last %)))
       (map read-string)
       (apply +)))

;; 第二題卡了一下，雖然加了 one 到 nine 的對照表，但是如果依序去處理的話，有機會遇到 edge case 像是 `eighthree` 應該要是 83 但是可能變成 88
;; 重新思考之後，就決定從頭找到第一個符合的，然後從尾巴找回第一個符合的，然後合起來把數字找到
;; 不過覺得第一個版本的 mapping / regexp (rule 1 / rule 2 / rule 3) 有點醜就是了

(defn convert [word]
  (if ((set (keys mapping)) word)
    (get mapping word)
    word))

(defn find-number [line]
  (->> [(re-find rule-2 line)
        (re-find rule-3 (str/reverse line))]
       (map convert)
       (apply str)
       (read-string)))

(comment
  (find-number "4nineeightseven2"))

(defn part2 [records]
  (->> records
       (map find-number)
       (apply +)))

(deftest test-example
  (is (= 142 (part1 example)))
  (is (= 281 (part2 example-2))))

; ## Results
{:nextjournal.clerk/visibility {:result :show}}

(part1 example)
(part2 example-2)

(time (part1 input)) ; 54605, Elapsed time: 0.463584 msecs
(time (part2 input)) ; 55429, Elapsed time: 0.435542 msecs

