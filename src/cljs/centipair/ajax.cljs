(ns centipair.ajax
  (:require [ajax.core :refer [GET POST json-request-format edn-request-format]]
            [centipair.utils :as utils])
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
          :error-handler error-handler
          :format (json-request-format)
          :headers {:X-CSRF-Token (utils/get-value "csrfmiddlewaretoken")})))
