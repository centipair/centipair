(ns centipair.registry
  (:require [centipair.user :as user]
            [centipair.admin.menu :as menu])
  )


(def function-registry {:render-register-form user/render-register-form
                        :render-admin-side-menu menu/render-side-menu})



(defn ^:export load-function [name]
  (((keyword name) function-registry)))
