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

:input test-data
:output '(1 0 1 1 0)
(defn most-common-bits [binaries]
  (let [half (-> binaries count (/ 2))]
    (->> binaries
         (map str->bits)
         (apply map +) ;; test-data -> '(7 5 8 7 5)
         (map #(if (> % half) 1 0)))))

:input  '(1 0 1 1 0)
:output '(0 1 0 0 1)
(defn flip-bits [bits]
  (map #(bit-xor 1 %) bits))

:input '(1 0 1 1 0)
:output 22
(defn bits->int [bits]
  (read-string (apply str "2r" bits)))

(def part-1
  (let [gamma-bits   (most-common-bits data)
        epsilon-bits (flip-bits gamma-bits)
        gamma   (bits->int gamma-bits)
        epsilon (bits->int epsilon-bits)]
    (* gamma epsilon)))
