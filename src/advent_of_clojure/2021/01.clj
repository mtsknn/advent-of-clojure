(ns advent-of-clojure.2021.01
  (:require [advent-of-clojure.util :as util]))

(def test-data
  [199 200 208 210 200 207 240 269 260 263])

(def data
  (->> (util/read-input-lines 2021 01)
       (map #(Integer/parseInt %))))

;; The `:input` and `:output` show example input and output.
;; The values are evaluated but discarded,
;; so they don't affect the Clojure program in any way.
;; I learned the trick from Fred Overflow:
;; https://youtu.be/orFYFwiG1dM
:input 2 [1 2 3 4 5]
:output '((1 2) (2 3) (3 4) (4 5))
:input 3 [1 2 3 4 5]
:output '((1 2 3) (2 3 4) (3 4 5))
(defn group [n numbers]
  (partition n 1 numbers))

:input [1 2]
:output true
(defn increasing? [[a b]]
  (> b a))

(def part-1
  (->> data
       (group 2)
       (filter increasing?)
       count))

(def part-2
  (->> data
       (group 3)
       (map #(apply + %))
       (group 2)
       (filter increasing?)
       count))
