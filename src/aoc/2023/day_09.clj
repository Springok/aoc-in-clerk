;; # 🎄 AOC 2023, Day 9: Mirage Maintenance
^{:nextjournal.clerk/visibility :hide-ns}
(ns aoc.2023.day-09
  {:nextjournal.clerk/toc true}
  (:require [nextjournal.clerk :as clerk]
            [aoc.util :as util]
            [clojure.test :refer [deftest is]]))

;; ## After
;;  - `2023-12-09` - 覺得 Part2 應該還是有更好的解法才對，覺得自己不知道卡在哪裡...
;;  - `2023-12-09` - 看了別人的解答之後，發現我根本就把 record 反過來 (reverser) 做就好啦...用不到 `left-most`

;; ## Problem
;; 看起來又是個 iteration 的遊戲啦，然後要一層層反推

;; ## Solution
;; ### Part 1

;; 紀錄下每一步的變化吧，然後再 reduce + last + 就可以解掉了

;; parse 這邊要小心，後來發現要處理負數，然後也在加了個空白才順利解掉，太笨了，卡了一下...
{:nextjournal.clerk/visibility {:result :hide}}
(defn parse-input [file]
  (->> (util/read-file-by-line file)
       (map #(re-seq #".\d+" (str " " %)))
       (map #(map read-string %))))

;; Example
{:nextjournal.clerk/visibility {:result :show}}
(parse-input "../resources/aoc/2023/day9-ex.txt")
{:nextjournal.clerk/visibility {:result :hide}}

;; 核心的 iteration function, 會產生所有 history records 直到全都是 0
(defn process [history]
  (loop [history [history]]
    (let [current-record (last history)
          next-record (->> (partition 2 1 current-record)
                           (map #(apply - (reverse %))))]
      (if (every? zero? current-record)
        (->> history
             (map last)
             (apply +))
        (recur (conj history next-record))))))

(defn part1 [input]
  (->> (parse-input input)
       (map process)
       (reduce +)))

;; ### Part 2
(defn part2 [input]
  (->> (parse-input input)
       (map reverse)
       (map process)
       (reduce +)))

;; ## Answer

;; ### Part 1
{:nextjournal.clerk/visibility {:code :show :result :show}}
(part1 "../resources/aoc/2023/day9.txt")

{:nextjournal.clerk/visibility {:code :hide :result :show}}
(with-out-str
  (time (part1 "../resources/aoc/2023/day9.txt")))
;
;; ### Part 2
{:nextjournal.clerk/visibility {:code :show :result :show}}
(part2 "../resources/aoc/2023/day9.txt")

{:nextjournal.clerk/visibility {:code :hide :result :show}}
(with-out-str
  (time (part2 "../resources/aoc/2023/day9.txt")))

;; ---
;; ## Example

{:nextjournal.clerk/visibility {:code :show :result :hide}}

(deftest test-example
  (is (= 114 (part1 "../resources/aoc/2023/day9-ex.txt")))
  (is (= 2 (part2 "../resources/aoc/2023/day9-ex.txt"))))

;; ## Obsoleted in first iteration
;; 原本看覺得蠻簡單的，結果加上 parse 的處理錯誤，硬是多卡了我 30 分鐘，好無言呀...
;; 弄了一個 left-most 來反推每筆 history 對應的數字
(defn left-most [history]
  (loop [history history]
    (if (= 2 (count history))
      (apply - history)
      (recur (conj (vec (drop-last 2 history)) (apply - (take-last 2 history)))))))
