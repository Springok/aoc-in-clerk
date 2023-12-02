(ns user
  (:require [nextjournal.clerk :as clerk]
            [aoc.index :as index]))

(clerk/serve! {:port 7878 :browse true})

(comment
  (clerk/build! {:index "aoc/index.clj" :paths (index/build-paths) :browse true}))

