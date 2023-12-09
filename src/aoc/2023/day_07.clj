;; # 🎄 AOC 2023, Day 7: Camel Cards
^{:nextjournal.clerk/visibility :hide-ns}
(ns aoc.2023.day-07
  {:nextjournal.clerk/toc true}
  (:require [nextjournal.clerk :as clerk]
            [aoc.util :as util]
            [clojure.string :as cs]
            [clojure.test :refer [deftest is]]))

{:nextjournal.clerk/visibility {:result :hide}}
;; ## Problem
;; 有趣的題目呀～玩梭哈啦～
;; 應該是要弄一個客製化的 sort by 出來

;; ## Example Data / Input

(defn parse-input [file]
  (->> (util/read-file-by-line file)
       (map #(cs/split % #" "))))

;; {:rank [hands bid]}

(def rules
  [])

(comment
  (parse-input "../resources/aoc/2023/day7-ex.txt"))
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
