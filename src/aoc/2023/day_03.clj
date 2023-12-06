;; # 🎄 AOC 2023, Day 3: Gear Ratios
^{:nextjournal.clerk/visibility :hide-ns}
(ns aoc.2023.day-03
  {:nextjournal.clerk/toc true}
  (:require [nextjournal.clerk :as clerk]
            [aoc.util :as util]
            [clojure.string :as str]
            [clojure.test :refer [deftest is]]))

{:nextjournal.clerk/visibility {:result :hide}}

;; ## Problem
;; 找出沒有 symbol 相鄰的數字，然後加總，所以要有座標系統跟每個數字的 neighbourhood
;; 可以先嘗試先建立 symbol 的 map，然後再建立數字的 map
;; 或是一行行找數字，然後看 neighbourhood 有沒有不是 . / 數字的


;; ## Example Data / Input

(defn parse-input [file]
  (->> (util/read-file-by-line file)))

(defn number-map [data]
  (for [j (range (count data))]
    (map-indexed (fn [i v]
                   {(set [[i j]]) v})
                 (str/split (nth data j) #""))))

(comment
  (map read-string (re-seq #"\d+|.+" "435*...."))
  (flatten (number-map (parse-input "../resources/aoc/2023/day3-ex.txt")))
  (number-map (parse-input "../resources/aoc/2023/day3.txt")))

;; ## Solution

; (defn part-nubmer? [set map]
;   (every? (fn [coord]
;              (= "." (val ) set))))

;; ### Part 1

;; ### Part 2

;; ## Answer

;; ### Part 1
; {:nextjournal.clerk/visibility {:code :show :result :show}}
; (part1 input-games)
;
; {:nextjournal.clerk/visibility {:code :hide :result :show}}
; (with-out-str
;   (time (part1 input-games)))
;
;; ### Part 2
; {:nextjournal.clerk/visibility {:code :show :result :show}}
; (part2 input-games)
;
; {:nextjournal.clerk/visibility {:code :hide :result :show}}
; (with-out-str
;   (time (part2 input-games)))

;; ---
;; ## Example

; (deftest test-example ;
;   (is (= 8 (part1 example-games)))
;   (is (= 2286 (part2 example-games))))
;
; {:nextjournal.clerk/visibility {:code :show :result :show}}
; (part1 example-games)
;
; {:nextjournal.clerk/visibility {:code :show :result :show}}
; (part2 example-games)

;; ## After
;; - spent 1.5 hour, still not figuring out the implementation...
