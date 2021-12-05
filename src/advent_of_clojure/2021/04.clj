(ns advent-of-clojure.2021.04
  (:require [advent-of-clojure.util :as util]
            [clojure.string :as str]))

(def test-data
  (str/join "\n" ["7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1"
                  ""
                  "22 13 17 11  0"
                  " 8  2 23  4 24"
                  "21  9 14 16  7"
                  " 6 10  3 18  5"
                  " 1 12 20 15 19"
                  ""
                  " 3 15  0  2 22"
                  " 9 18 13 17  5"
                  "19  8  7 25 23"
                  "20 11 10 24  4"
                  "14 21 16 12  6"
                  ""
                  "14 21 17 24  4"
                  "10 16 15  9 19"
                  "18  8 23 26 20"
                  "22 11 13  6  5"
                  " 2  0 12  3  7"]))

(def data
  (util/read-input 2021 04))

:input "5"
:output 5
(defn parse-int [string]
  (Integer/parseInt string))

:input test-data
:output '(7 4 9 5 11 ,,,)
(defn get-draws [string]
  (-> (str/split-lines string)
      first
      (str/split #",")
      (->> (map parse-int))))

:input  '([1 2 3] [4 5 6] [7 8 9])
:output '([1 4 7] [2 5 8] [3 6 9])
(defn cols [rows]
  ;; Found this accidentally via https://clojuredocs.org/clojure.core/map
  (apply map vector rows))

:input          "1 2 3\n 4 5 6\n 7 8 9"
:output {:rows '([1 2 3] [4 5 6] [7 8 9])
         :cols '([1 4 7] [2 5 8] [3 6 9])}
(defn parse-board [string]
  (let [rows (->> (str/split-lines string)
                  (map #(as-> % line
                              (str/trim line)
                              (str/split line #"\s+")
                              (mapv parse-int line))))]
    {:rows rows
     :cols (cols rows)}))

:input test-data
:output '({:rows '([22 13 17 11  0] [ 8  2 23  4 24] ,,,)
           :cols '([22  8 21  6  1] [13  2  9 10 12] ,,,)}) ;; + 2 more boards
(defn get-boards [string]
  (->> (str/split string #"(\r?\n){2}")
       (drop 1) ;; Draws row
       (map parse-board)))

:input  '([1 2 3] [4 5 6] [7 8 9]), 6
:output '((1 2 3) (4 5)   (7 8 9))
(defn remove-number [numbers n]
  (map #(remove #{n} %) numbers))

:input  {:rows '([1 2 3] [4 5 6] [7 8 9])
         :cols '([1 4 7] [2 5 8] [3 6 9])}, 4
:output {:rows '([1 2 3] [  5 6] [7 8 9])
         :cols '([1   7] [2 5 8] [3 6 9])}
(defn play-board [board draw]
  (-> board
      (update :rows remove-number draw)
      (update :cols remove-number draw)))

(defn play-boards [boards draw]
  (map #(play-board % draw) boards))

(defn winner? [board]
  (or (some empty? (:rows board))
      (some empty? (:cols board))))

(defn score [board draw]
  (let [unmarked-numbers (apply concat (:rows board))
        sum (apply + unmarked-numbers)]
    (* sum draw)))

(def part-1
  (loop [boards (get-boards data)
         draws (get-draws data)]
    (let [draw (first draws)
          new-boards (play-boards boards draw)
          winner (first (filter winner? new-boards))]
      (if winner
        (score winner draw)
        (recur new-boards (rest draws))))))

(def part-2
  (loop [boards (get-boards data)
         draws (get-draws data)]
    (let [draw (first draws)
          new-boards (play-boards boards draw)
          last-winner? (and (= 1 (count new-boards))
                            (winner? (first new-boards)))]
      (if last-winner?
        (score (first new-boards) draw)
        (recur (remove winner? new-boards) (rest draws))))))
