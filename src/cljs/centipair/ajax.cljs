(ns centipair.ajax
  (:require [ajax.core :refer [GET POST json-request-format edn-request-format]]
            [centipair.utils :as utils])
  (:use [centipair.components :only [notify]]))


(defn error-handler [response]
  (let [status (:status response)]
    (case status
      404 (notify 404)
      500 (notify 500)
      422 (notify 422 "The submitted data is not valid"))))


(defn post [url params function-handler]
  (do
    (notify 102 "Loading")
    (POST url
          :params params
          :handler function-handler
          :error-handler error-handler
          :format (json-request-format)
          ;;:headers {:X-CSRF-Token (utils/get-value "csrfmiddlewaretoken")}
          )))
