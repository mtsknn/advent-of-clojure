(ns advent-of-clojure.util
  (:require [clojure.string :as str]))

(defn read-input-lines [year day]
  (->> (format "resources/inputs/%d/%02d.txt" year day)
       slurp
       str/split-lines))
