;; # 🎄 AOC 2023, Day 6: Wait For It
^{:nextjournal.clerk/visibility :hide-ns}
(ns aoc.2023.day-06
  {:nextjournal.clerk/toc true}
  (:require [nextjournal.clerk :as clerk]
            [aoc.util :as util]
            [clojure.test :refer [deftest is]]))

{:nextjournal.clerk/visibility {:result :hide}}

;; ## Problem
;; 看起來是排列組合的問題吧，根據規則來計畫滿足結果的組合數，所以應該是要用 iteration

;; ## Solution
;; ### Part 1

;; Part 1 是要我們把得到的結果相乘

(defn parse-input [file]
  (->> (util/read-file-by-line file)
       (map #(re-seq #"\d+" %))
       (apply map vector)
       (map #(map read-string %))))

;; Example
{:nextjournal.clerk/visibility {:result :show}}
(parse-input "../resources/aoc/2023/day6-ex.txt")

{:nextjournal.clerk/visibility {:result :hide}}
(defn win? [t tt d]
  (let [t' (- tt t)]
    (> (* t' t) d)))

{:nextjournal.clerk/visibility {:result :show}}
;; Example
(win? 1 7 9)

{:nextjournal.clerk/visibility {:result :hide}}
(defn race [[tt d]]
  (loop [ts (range (inc tt))
         ways 0]
    (let [t (first ts)
          next-ts (drop 1 ts)]
      (if (empty? ts)
       ways
       (recur next-ts (if (win? t tt d) (inc ways) ways))))))

{:nextjournal.clerk/visibility {:result :show}}
;; Example
(race [7 9])

{:nextjournal.clerk/visibility {:result :hide}}
(defn part1 [input]
  (->> (parse-input input)
       (map race)
       (reduce *)))

;; ### Part 2

;; Part2 這裡我就用暴力解，只是調整一下 parse 的方式就用同樣的 race function 來做，的確是需要跑一下，可以往下拉，看時間。

{:nextjournal.clerk/visibility {:code :show :result :hide}}
(defn parse-input-part2 [file]
  (->> (util/read-file-by-line file)
       (map #(re-seq #"\d+" %))
       (map #(apply str %))
       (map read-string)))

;; Example
{:nextjournal.clerk/visibility {:result :show}}
(parse-input-part2 "../resources/aoc/2023/day6-ex.txt")

{:nextjournal.clerk/visibility {:result :hide}}
(defn part2 [input]
  (race (parse-input-part2 input)))

;; ## Answer

;; ### Part 1
{:nextjournal.clerk/visibility {:code :show :result :show}}
(part1 "../resources/aoc/2023/day6.txt")

{:nextjournal.clerk/visibility {:code :hide :result :show}}
(with-out-str
  (time (part1 "../resources/aoc/2023/day6.txt")))

;; ### Part 2
{:nextjournal.clerk/visibility {:code :show :result :show}}
(part2 "../resources/aoc/2023/day6.txt")

{:nextjournal.clerk/visibility {:code :hide :result :show}}
(with-out-str
  (time (part2 "../resources/aoc/2023/day6.txt")))

;; ---
;; ## Example

{:nextjournal.clerk/visibility {:result :hide}}
(deftest test-example ;
  (is (= 288 (part1 "../resources/aoc/2023/day6-ex.txt")))
  (is (= 71503 (part2 "../resources/aoc/2023/day6-ex.txt"))))

{:nextjournal.clerk/visibility {:code :show :result :show}}
(part1 "../resources/aoc/2023/day6-ex.txt")

{:nextjournal.clerk/visibility {:code :show :result :show}}
(part2 "../resources/aoc/2023/day6-ex.txt")

;; ## Reference
;;   - https://stackoverflow.com/questions/6135764/when-to-use-zipmap-and-when-map-vector

