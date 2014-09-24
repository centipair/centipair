(ns centipair.registry
  (:require [centipair.user :as user])
  )


(def function-registry {:render-register-form user/render-register-form})



(defn ^:export load-function [name]
  (((keyword name) function-registry)))

;;(((keyword (js/loadFunctionRegistry)) function-registry))
