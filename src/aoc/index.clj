;; # 🎄 Advent of Code 2023

;; [Advent of Code](https://adventofcode.com) with
;; [Clerk](https://clerk.vision).

(ns aoc.index
  {:nextjournal.clerk/visibility {:code :hide :result :hide}}
  (:require [babashka.fs :as fs]
            [clojure.string :as str]
            [nextjournal.clerk :as clerk]))

#_(days-with-contents)

(defn build-paths
  []
  (into []
        (keep (fn [day]
                (let [f (fs/file "src" "aoc" "2023" (format "day_%s.clj" (cond->> day (<= day 10) (str "0"))))]
                  (when (and (.exists f)
                             (< 3 (count (str/split-lines (slurp f)))))
                    (str f)))))
        (range 25)))


#_(build-paths)

{:nextjournal.clerk/visibility {:result :show}}

^::clerk/no-cache
(clerk/html (into [:ul] (mapv (fn [path]
                                (when-let [day (second (re-matches #".*day_(\d+).clj" path))]
                                 [:li [:a {:href (-> path
                                                     (str/replace ".clj" "")
                                                     clerk/doc-url)} "Day " day (if ((set ["03" "05" "07"]) day) " (Unsolved)" "")]])) (build-paths))))
