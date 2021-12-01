(ns advent-of-clojure.2021.01
  (:require [clojure.string :as str]))

; Test data
(def data
  [199 200 208 210 200 207 240 269 260 263])

(def data
  (->> "src/advent_of_clojure/2021/01-input.txt"
       slurp
       str/split-lines
       (map #(Integer/parseInt %))))

;; The `:input` and `:output` show example input and output.
;; The values are evaluated but discarded,
;; so they don't affect the Clojure program in any way.
;; I learned the trick from Fred Overflow:
;; https://youtu.be/orFYFwiG1dM
:input [1 2 3 4]
:output '((1 2) (2 3) (3 4))
(defn create-pairs [numbers]
  (partition 2 1 numbers))

:input [1 2]
:output true
(defn increasing? [[a b]]
  (> b a))

(def part-1
  (->> data
       create-pairs
       (filter increasing?)
       count))

:input [1 2 3 4 5]
:output '((1 2 3) (2 3 4) (3 4 5))
(defn create-groups-of-3 [numbers]
  (partition 3 1 numbers))

(def part-2
  (->> data
       create-groups-of-3
       (map #(apply + %))
       create-pairs
       (filter increasing?)
       count))
