;; # 🎄 AOC 2023, Day 8: Haunted Wasteland
^{:nextjournal.clerk/visibility :hide-ns}
(ns aoc.2023.day-08
  {:nextjournal.clerk/toc true}
  (:require [nextjournal.clerk :as clerk]
            [aoc.util :as util]
            [clojure.string :as cs]
            [clojure.math.numeric-tower :as math]))

;; ## Problem
;; 感覺就是根據 input 然後用 iteration 來跑到終點


;; ## Solution
;; ### Part 1

;; 先處理 data 的格式

;; 首先是每個 map 對應的左右

{:nextjournal.clerk/visibility {:result :hide}}
(defn parse-maps [maps]
  (->> maps
      (map #(re-seq #"[A-Z]{3}" %))
      (map (fn [[k & vl]]
             {k vl}))
      (reduce (fn [v r]
                (merge r v)))))

;; 再來是處理 instruction
(defn parse-input [file]
  (let [[instructions _ & maps] (util/read-file-by-line file)]
    [instructions (parse-maps maps)]))

;; Example
{:nextjournal.clerk/visibility {:result :show}}
(parse-input "../resources/aoc/2023/day8-ex.txt")
{:nextjournal.clerk/visibility {:result :hide}}

;; process 是用來找到對應到 ends 的步數，從一個 start 開始，然後只要到達另一個 ends 就停下來
(defn process [instructions maps start ends]
   (loop [instructions (flatten (repeat (cs/split instructions #"")))
          current-node start
          steps 0]
     (let [direction (first instructions)
           next-nodes (get maps current-node)]
       (if (ends current-node) ;; reach any ends with "Z"
          {:steps steps :end-node current-node}
          (recur (drop 1 instructions) (if (= "R" direction)
                                           (last next-nodes)
                                           (first next-nodes)) (inc steps))))))

(defn part1 [input]
  (let [[instructions maps] (parse-input input)]
    (:steps (process instructions maps "AAA" #{"ZZZ"}))))

;; ### Part 2
;; #### Attempt 1 - 偏暴力解，先找到所有起點跟終點，然後一個個丟進去
;; 但是跑不完，應該要用最小公倍數之類的了

(defn attempt-1 [instructions maps starts ends]
   (loop [instructions (flatten (repeat (cs/split instructions #"")))
          current-nodes starts
          steps 0]
     (let [direction (first instructions)
           next-nodes (map #(get maps %) current-nodes)]
       (if (= (set current-nodes) ends)
          steps
          (recur (drop 1 instructions) (if (= "R" direction)
                                           (map last next-nodes)
                                           (map first next-nodes)) (inc steps))))))

;; #### Attempt 2 - 最小公倍數 / 最大公倍數
;; 解掉啦，不過有用到 Math 的 library 就是了

(defn part2 [input]
  (let [[instructions maps] (parse-input input)
        keys' (keys maps)
        starts (set (filter #(cs/ends-with? % "A") keys'))
        ends   (set (filter #(cs/ends-with? % "Z") keys'))
        steps (->> starts
                   (map #(process instructions maps % ends))
                   (map :steps))
        gcd (reduce math/gcd steps)
        divs (map #(/ % gcd) steps)]
     (reduce * (conj divs gcd))))

;; ## Answer

;; ### Part 1
{:nextjournal.clerk/visibility {:code :show :result :show}}
(part1 "../resources/aoc/2023/day8.txt")
{:nextjournal.clerk/visibility {:code :hide :result :show}}
(with-out-str
  (time (part1 "../resources/aoc/2023/day8.txt")))

;; ### Part 2
{:nextjournal.clerk/visibility {:code :show :result :show}}
(part2 "../resources/aoc/2023/day8.txt")

{:nextjournal.clerk/visibility {:code :hide :result :show}}
(with-out-str
  (time (part2 "../resources/aoc/2023/day8.txt")))
