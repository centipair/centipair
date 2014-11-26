(ns centipair.utils
  (:require [goog.dom :as gdom]))

(defn get-value
  [id]
  (.-value ( gdom/getElement id)))
