;; # 🎄 Advent of Code: 2023 Day 1: Trebuchet?!
^{:nextjournal.clerk/visibility :hide-ns}
(ns aoc.2023.day-01
  (:require
   [nextjournal.clerk :as clerk]
   [aoc.util :as util]
   [clojure.string :as str]
   [clojure.test :refer [deftest is]]))

{:nextjournal.clerk/visibility {:result :hide}}

;; ## Example Data / Input

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

;; ## (Iteration 1)

(def mapping
  (let [numbering ["one" "two" "three" "four" "five" "six" "seven" "eight" "nine"]]
    (zipmap
      (flatten [numbering (map str/reverse numbering)])
      (flatten (repeat 2 (range 1 (inc 9)))))))

(def rule-1
  #"\d{1}")

(def rule-2
 #"\d{1}|one|two|three|four|five|six|seven|eight|nine")

(def rule-3
 #"\d{1}|eno|owt|eerht|ruof|evif|xis|neves|thgie|enin")

;; ### Part 1
;; 第一題比較容易，直接用 regexp 的 `d{1}` 就好了，搭配 `re-seq` 基本上就可以把所有數字找到了，然後取頭取尾

(defn part1 [records]
  (->> records
       (map #(re-seq rule-1 %))
       (map #(str (first %) (last %)))
       (map read-string)
       (apply +)))

;; ### Part 2
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

(defn part2 [records]
  (->> records
       (map find-number)
       (apply +)))

;; ---
;; ## (Iteration 2)

;; 搭配 lookahead 是比較精簡的解法呀～不過也是需要轉換數字，但也就只要處理最後一個跟第一個的轉換就好啦～

;; ### ->digit
(def digits (->> ["one" "two" "three" "four" "five" "six" "seven" "eight" "nine"]
                 (map-indexed (fn [i w] [w (str (inc i))]))
                 (into {})))

(defn ->digit [word]
  (if ((set (keys digits)) word)
    (get digits word)
    word))

;; ### Solve
(defn solve [records rule]
  (->> records
       (map #(let [tokens (map last (re-seq rule %))
                   first-d (->digit (first tokens))
                   last-d  (->digit (last tokens))]
               (-> (str first-d last-d)
                   read-string)))
       (apply +)))

;; ### Part 1
(defn part1 [records]
  (solve records #"(?=(\d))"))

;; ### Part 2
(defn part2 [records]
  ;; lookahead
  (solve records #"(?=(\d|one|two|three|four|five|six|seven|eight|nine))"))

;; ## Answer

;; ### Part 1
{:nextjournal.clerk/visibility {:code :show :result :show}}
(part1 input)

{:nextjournal.clerk/visibility {:code :hide :result :show}}
(with-out-str
  (time (part1 input)))

;; ### Part 2
{:nextjournal.clerk/visibility {:code :show :result :show}}
(part2 input)

{:nextjournal.clerk/visibility {:code :hide :result :show}}
(with-out-str
  (time (part2 input)))

;; ---
;; ## Example
{:nextjournal.clerk/visibility {:code :hide :result :hide}}

(deftest test-example ;
  (is (= 142 (part1 example)))
  (is (= 281 (part2 example-2))))

{:nextjournal.clerk/visibility {:code :show :result :show}}
(part1 example)

{:nextjournal.clerk/visibility {:code :show :result :show}}
(part2 example-2)

