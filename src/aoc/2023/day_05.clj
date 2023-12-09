;; # 🎄 AOC 2023, Day 5: If You Give A Seed A Fertilizer
^{:nextjournal.clerk/visibility :hide-ns}
(ns aoc.2023.day-05
  {:nextjournal.clerk/toc true}
  (:require [nextjournal.clerk :as clerk]
            [aoc.util :as util]
            [clojure.string :as cs]
            [clojure.test :refer [deftest is]]
            [clojure.core :as c]))

;; ## After

;; ## Problem / Before
;; almanac
;; 看起來應該是要用 hash-map 來處理不同的 type
;; 繼續用 set 來代表位置應該也可以, 然後要產生 kv pairs 然後一個個往下
;; 光是要理解題目，就 15 ~ 20 分鐘去了

;; ## Example Data / Input

{:nextjournal.clerk/visibility {:result :hide}}
(defn parse-input [file]
  (->> (util/read-file-by-line file)))

;; ## Solution
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
; ;; ### Part 2
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
