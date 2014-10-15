(ns centipair.routes.api
  (:use compojure.core)
  (:require [liberator.core :refer [resource defresource]]))



(defresource check-email []
  :available-media-types ["application/json"]
  :handle-ok (fn [_] (generate-string (get-symbols source))))


(defroutes api-routes
  (GET "/api/check-email" [] (check-email))
