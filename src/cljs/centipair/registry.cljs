(ns centipair.registry
  (:require [centipair.user :as user])
  )


(def function-registry {:user_registration user/render-register-form})


(((keyword (js/loadFunctionRegistry)) function-registry))
