(ns advent-of-clojure.2021.03
  (:require [advent-of-clojure.util :as util]))

(def test-data
  ["00100"
   "11110"
   "10110"
   "10111"
   "10101"
   "01111"
   "00111"
   "11100"
   "10000"
   "11001"
   "00010"
   "01010"])

(def data
  (util/read-input-lines 2021 03))

:input "00100"
:output '(0 0 1 0 0)
(defn str->bits [string]
  (->> (seq string)
       (map #(if (= % \1) 1 0))))

:input '((0 0 1 0 0)
         (1 1 1 1 0)
         (1 0 1 1 0))
:output '(2 1 3 2 0)
(defn sum-bits [bits-list]
  (apply map + bits-list))

:input '((0 0 1 0 0)
         (1 1 1 1 0)
         (1 0 1 1 0))
:output '(1 0 1 1 0)
(defn most-common-bits [bits-list]
  (let [half (/ (count bits-list) 2)]
    (map
      #(if (>= % half) 1 0)
      (sum-bits bits-list))))

:input  '(1 0 1 1 0)
:output '(0 1 0 0 1)
(defn flip-bits [bits]
  (map #(bit-flip % 0) bits))

:input '(1 0 1 1 0)
:output 22
(defn bits->int [bits]
  (read-string (apply str "2r" bits)))

(def part-1
  (let [bits-list    (map str->bits test-data)
        gamma-rate   (most-common-bits bits-list)
        epsilon-rate (flip-bits gamma-rate)
        gamma-rate   (bits->int gamma-rate)
        epsilon-rate (bits->int epsilon-rate)]
    (* gamma-rate epsilon-rate)))

:input '((0 0 1 0 0)
         (1 1 1 1 0)
         (1 0 1 1 0))
:output '(0 1 0 0 1)
(defn least-common-bits [bits-list]
  (flip-bits (most-common-bits bits-list)))

(defn find-bits [bits-list filter-fn]
  (loop [index 0
         bits-list bits-list]
    (let [matchable-bit (nth (filter-fn bits-list) index)
          matching-bits (filter #(= (nth % index) matchable-bit) bits-list)]
      (if (= (count matching-bits) 1)
        (first matching-bits)
        (recur (inc index) matching-bits)))))

(def part-2
  (let [bits-list               (map str->bits data)
        oxygen-generator-rating (find-bits bits-list most-common-bits)
        co2-scrubber-rating     (find-bits bits-list least-common-bits)
        oxygen-generator-rating (bits->int oxygen-generator-rating)
        co2-scrubber-rating     (bits->int co2-scrubber-rating)]
    (* oxygen-generator-rating co2-scrubber-rating)))
