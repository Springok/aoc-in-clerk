;; # 🎄 Advent of Code: 2023 Day 2: Cube Conundrum
^{:nextjournal.clerk/visibility :hide-ns}
(ns aoc.2023.day-02
  (:require [nextjournal.clerk :as clerk]
            [aoc.util :as util]
            [clojure.string :as str]
            [clojure.test :refer [deftest is]]))

{:nextjournal.clerk/visibility {:result :hide}}

;; ## Example Data / Input
;; 光是資料處理就花了我大概一個小時呀.... 我總感覺應該有更好的方法可以直接分好 data 適合後面的處理

(def bag
  {"red" 12 "green" 13, "blue" 14})

(def example-games
  (->> (util/read-file-by-line "../resources/aoc/2023/day2-ex.txt")
       (map #(str/split % #"; "))
       (map (fn [game]
              (->> game
                   (map #(re-seq #"\d+ red|\d+ green|\d+ blue" %)))))))

(def input-games
  (->> (util/read-file-by-line "../resources/aoc/2023/day2.txt")
       (map #(str/split % #"; "))
       (map (fn [game]
              (->> game
                   (map #(re-seq #"\d+ red|\d+ green|\d+ blue" %)))))))

;; `example-games`
{:nextjournal.clerk/visibility {:code :hide :result :show}}
example-games

{:nextjournal.clerk/visibility {:result :hide}}

;; ## (Iteration 1)
;; ### Part 1

;; 這邊想說用 string 硬幹 + split 來做到 filter 的效果
{:nextjournal.clerk/visibility {:code :show :result :hide}}
(defn possible? [set bag-set]
  (->> (map #(str/split % #" ") set)
       (every? (fn [[n color]]
                 (<= (read-string n) (get bag-set color))))))

;; Examples
{:nextjournal.clerk/visibility {:code :show :result :show}}
(possible? ["4 blue" "3 red"] bag)
(possible? ["8 green" "6 blue" "20 red"] bag)

{:nextjournal.clerk/visibility {:result :hide}}
;; 所以 part1 只是用 `map-indexed` 然後搭配上面的 filter function 紀錄下 game x 然後加總，做到我要的效果
(defn part1 [games]
  (->> games
       (map-indexed (fn [idx game]
                      (if (->> game
                               (every? (fn [set]
                                         (possible? set bag))))
                        (inc idx)
                        0)))
       (apply +)))

;; ### Part 2
;; 最後屈服了啦，還是把每次的 game set 轉成適合的 map 來處理，如下
(defn set->map [set]
  (->> set
       (map #(str/split % #" "))
       (map (fn[[n color]]
               [color (read-string n)]))
       (into (sorted-map))))

;; Examples
{:nextjournal.clerk/visibility {:code :show :result :show}}
(set->map ["3 blue" "4 red"])

{:nextjournal.clerk/visibility {:result :hide}}
;; `merge-with` 真的太讚了啦，秒解勒
;; 用來計算每局 game 的 power
(defn power [game]
  (->> game
       (map set->map)
       (apply merge-with max)
       vals
       (apply *)))

;; 然後加總
(defn part2 [games]
  (->> games
       (map power)
       (apply +)))

;; ## Answer

;; ### Part 1
{:nextjournal.clerk/visibility {:code :show :result :show}}
(part1 input-games)

{:nextjournal.clerk/visibility {:code :hide :result :show}}
(with-out-str
  (time (part1 input-games)))

;; ### Part 2
{:nextjournal.clerk/visibility {:code :show :result :show}}
(part2 input-games)

{:nextjournal.clerk/visibility {:code :hide :result :show}}
(with-out-str
  (time (part2 input-games)))

;; ---
;; ## Example
{:nextjournal.clerk/visibility {:code :hide :result :hide}}

(deftest test-example ;
  (is (= 8 (part1 example-games)))
  (is (= 2286 (part2 example-games))))

{:nextjournal.clerk/visibility {:code :show :result :show}}
(part1 example-games)

{:nextjournal.clerk/visibility {:code :show :result :show}}
(part2 example-games)
