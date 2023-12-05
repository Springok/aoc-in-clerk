;; # 🎄 AOC 2023, Day 04: Scratchcards
^{:nextjournal.clerk/visibility :hide-ns}
(ns aoc.2023.day-04
  {:nextjournal.clerk/toc true}
  (:require [nextjournal.clerk :as clerk]
            [aoc.util :as util]
            [clojure.string :as str]
            [clojure.set :as cset]
            [clojure.test :refer [deftest is]]))

{:nextjournal.clerk/visibility {:result :hide}}

;; ## Example Data / Input
;; 看起來今天的題目比前一天友善一點了，像是 bingo 遊戲的感覺，然後兩組來比較，然後計算 winning number 的數量，進而得到最後的分數
;; 關鍵應該是要用 `set` 來做 intersection 就能解掉了
(defn parse-input [file]
  (->> (util/read-file-by-line file)
       (map (fn [line]
              (let [[_ numbers] (str/split line #":")]
                numbers)))
       (map (fn [line]
              (let [[n w] (str/split line #"\|")
                    n (set (map read-string (re-seq #"\d+" n)))
                    w (set (map read-string (re-seq #"\d+" w)))]
                 [n w])))))

;; `parse-input` demo
{:nextjournal.clerk/visibility {:code :show :result :show}}
(parse-input  "../resources/aoc/2023/day4-ex.txt")

;; ## Solution
;; ### Part 1
;; 不過 Part 1 也是花了大概快一小時呀，指令太不熟了啦

{:nextjournal.clerk/visibility {:result :hide}}

(defn part1 [input]
  (->> (parse-input input)
       (map (fn [[n w]]
              (cset/intersection n w)))
       (map (fn [set]
              (let [count (count set)]
                (if (zero? count) 0 (reduce * (repeat (dec count) 2))))))
       (apply +)))

;; ### Part 2
;; 大概又花我了接近兩小時... 中間一半時間都在搞懂題目跟找出規律性...
;; 題目好難懂呀 XD
;; 好像有點類似複製的概念一樣，感覺是要用 iteration 來解題目了...
;; 知道什麼時候是 end case 然後停下來，回傳結果出去加總, 也有點像有一年的魚再生魚的處理（？）
;; 找出規律性，就是轉換資料結構，先搞清楚 original card 會贏接下來的那幾局

;; 依序解開每張卡片
(defn process-card [numbers card-n]
  (let [winning (nth numbers card-n) ; locate winning set
        num-count (count (filter #(= (inc card-n) %) winning)) ; caculate the winning instance
        numbers (assoc numbers card-n num-count)]
    (reduce (fn [numbers next-card]
              (update numbers (dec next-card) concat (repeat num-count next-card)))
            numbers
            (remove #(= (inc card-n) %) winning))))

;; 迭代解開每張卡片
(defn solve [numbers]
  (loop [numbers numbers
         idx 0]
    (if (= idx (count numbers))
      numbers
      (recur (process-card numbers idx) (inc idx)))))

{:nextjournal.clerk/visibility {:code :show :result :show}}
(solve [[1 2 3 4 5] [2 3 4] [3 4 5] [4 5] [5] [6]])

;; 最後合在一起
{:nextjournal.clerk/visibility {:result :hide}}

(defn part2 [input]
  (->> (parse-input input)
       (map (fn [[n w]]
              (cset/intersection n w)))
       (map-indexed (fn [i numbers] ; 這邊是轉換資料成該卡片，會贏接下來的哪幾局，方便之後計算
                      (let [idx (inc i)]
                        (range idx (+ idx (inc (count numbers)))))))
       vec ; 有點 tricky 要先轉成 vector (index-based) 才能用後續的 assoc / update
       solve ; iternation 依序解開 card
       (apply +)))

;; ## Answer

;; ### Part 1
{:nextjournal.clerk/visibility {:code :show :result :show}}
(part1 "../resources/aoc/2023/day4.txt")
;
{:nextjournal.clerk/visibility {:code :hide :result :show}}
(with-out-str
  (time (part1 "../resources/aoc/2023/day4.txt")))

;; ### Part 2
{:nextjournal.clerk/visibility {:code :show :result :show}}
(part2 "../resources/aoc/2023/day4.txt")

{:nextjournal.clerk/visibility {:code :hide :result :show}}
(with-out-str
  (time (part2 "../resources/aoc/2023/day4.txt")))

;; ---
;; ## Example

{:nextjournal.clerk/visibility {:result :hide}}
(deftest test-example ;
  (is (= 8 (part1 "../resources/aoc/2023/day4-ex.txt")))
  (is (= 40 (part2 "../resources/aoc/2023/day4-ex.txt"))))

{:nextjournal.clerk/visibility {:code :show :result :show}}
(part1 "../resources/aoc/2023/day4-ex.txt")
;
{:nextjournal.clerk/visibility {:code :show :result :show}}
(part2 "../resources/aoc/2023/day4-ex.txt")

;; ## After
;; 看了這個解法 [AOC - Day 4](https://www.youtube.com/watch?v=tXnPMSSQgCU)，覺得自己真的是太淺了呀...不過別人是世界前幾的神人就是了🫡
