(ns advent-of-clojure.2021.02
  (:require [advent-of-clojure.util :as util]
            [clojure.string :as str]))

(def test-data
  [[:forward 5]
   [:down    5]
   [:forward 8]
   [:up      3]
   [:down    8]
   [:forward 2]])

(def data
  (->> (util/read-input-lines 2021 02)
       (map #(str/split % #" "))
       (map (fn [[direction amount]]
              [(keyword direction)
               (Integer/parseInt amount)]))))

(defn follow-commands [commands]
  (reduce
    (fn [position [direction amount]]
      (case direction
        :forward (update position :distance + amount)
        :down    (update position :depth    + amount)
        :up      (update position :depth    - amount)))
    {:distance 0 :depth 0}
    commands))

;; A silly name but whatever ¯\(ツ)/¯
(defn distance-*-depth [{:keys [distance depth]}]
  (* distance depth))

(def part-1
  (->> data
       follow-commands
       distance-*-depth))

(defn follow-commands-2 [commands]
  (reduce
    (fn [{:keys [aim] :as position} [direction amount]]
      (case direction
        :down    (update position :aim + amount)
        :up      (update position :aim - amount)
        :forward (-> position
                     (update :distance + amount)
                     (update :depth    + (* aim amount)))))
    {:distance 0 :depth 0 :aim 0}
    commands))

(def part-2
  (->> data
       follow-commands-2
       distance-*-depth))
