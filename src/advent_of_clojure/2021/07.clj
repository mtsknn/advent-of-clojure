(ns advent-of-clojure.2021.07
  (:require [advent-of-clojure.util :as util]))

(def test-data
  "16,1,2,0,4,2,7,1,2,14")

(def data
  (util/read-input 2021 07))

(defn parse [string]
  (->> string
       (re-seq #"\d+")
       (map #(Integer/parseInt %))))

;; Wow, this is ridiculously faster than `Math/abs`.
;; By changing `Math/abs` to this,
;; Part 1 speeds up on my machine from ~11,000ms to ~100ms!
;; Java interop is slow, yes,
;; but the difference here surprised me.
;; Fn from https://stackoverflow.com/a/21756221/1079869
(defn abs [n]
  (max n (- n)))

(defn distance [from to]
  (abs (- from to)))

;; Using this speeds up Part 2 on my machine
;; from ~12,000ms to ~700ms
(def range-sum
  (memoize
    (fn [end]
      (apply + (range 1 end)))))

(defn expensive-distance [from to]
  (->> (distance from to)
       inc
       range-sum))

(defn total-distance [distance-fn positions target-position]
  (reduce
    (fn [result position]
      (+ result (distance-fn position target-position)))
    0
    positions))

(defn solve [distance-fn]
  (let [positions (parse data)
        from    (apply min positions)
        to (inc (apply max positions))]
    (->> (range from to)
         (map (partial total-distance distance-fn positions))
         (apply min))))

(def part-1 (solve distance))
(def part-2 (solve expensive-distance))
