(ns centipair.ajax
  (:require [ajax.core :refer [GET POST]])
  (:use [centipair.components :only [notify]]))


(defn error-handler [response]
  (.log js/console "error")
  (notify 404)
  true
  )


(defn post [url params function-handler]
  (do
    (notify 102 "Loading")
    (POST url
          :params params
          :handler function-handler
          :error-handler error-handler)))
