(ns advent-of-clojure.util
  (:require [clojure.string :as str]))

(defn read-input [year day]
  (slurp (format "resources/inputs/%d/%02d.txt" year day)))

(defn read-input-lines [year day]
  (str/split-lines (read-input year day)))
