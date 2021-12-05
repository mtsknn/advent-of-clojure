(ns advent-of-clojure.2021.05
  (:require [advent-of-clojure.util :as util]))

(def test-data
  ["0,9 -> 5,9"
   "8,0 -> 0,8"
   "9,4 -> 3,4"
   "2,2 -> 2,1"
   "7,0 -> 7,4"
   "6,4 -> 2,0"
   "0,9 -> 2,9"
   "3,4 -> 1,4"
   "0,0 -> 8,8"
   "5,5 -> 8,2"])

(def data
  (util/read-input-lines 2021 05))

(defn parse-int [string]
  (Integer/parseInt string))

:input ["0,9 -> 5,9"
        "6,4 -> 2,0"]
:output ({:x1 0, :x2 5, :y1 9, :y2 9}
         {:x1 6, :x2 2, :y1 4, :y2 0})
(defn fissures [string]
  (->> string
       (map #(re-seq #"\d+" %))
       (map #(map parse-int %))
       (map (fn [[x1 y1 x2 y2]] {:x1 x1, :x2 x2, :y1 y1, :y2 y2}))))

(defn straight? [{:keys [x1 x2 y1 y2]}]
  (or (= x1 x2) (= y1 y2)))

:input 1, 3
:output '(1 2 3)
:input 3, 1
:output '(3 2 1)
:input 1, 1
:output '(1)
;; Naming is difficult ;_;
(defn my-range [a b]
  (if (> b a)
    (range a (inc b))
    (range a (dec b) -1)))

:input {:x1, 1 :x2, 3 :y1, 0 :y2 0}
:output '((1 0) (2 0) (3 0))
:input {:x1, 1 :x2, 3 :y1, 6 :y2 4}
:output '((1 6) (2 5) (3 4))
(defn points [{:keys [x1 x2 y1 y2] :as fissure}]
  (let [xs (my-range x1 x2)
        ys (my-range y1 y2)]
    (if (straight? fissure)
      (for [x xs, y ys] (list x y))
      (partition 2 (interleave xs ys)))))

:input  '((0 0) (0 1) (0 1))
:output {'(0 0) 1, '(0 1) 2}
(defn plot-points [coords]
  (reduce
    (fn [points coord] (update points coord (fnil inc 0)))
    {}
    coords))

:input {'(0 0) 1, '(0 1) 2}
:output 1
(defn count-overlapping-points [points]
  (->> points
       (remove #(= 1 (val %)))
       count))

(defn solve [straight-only?]
  (->> (fissures data)
       (filter (if straight-only? straight? identity))
       (mapcat points)
       plot-points
       count-overlapping-points))

(def part-1 (solve true))
(def part-2 (solve false))
