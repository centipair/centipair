(ns centipair.user
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [goog.dom :as gdom])
  (:use [centipair.components :only [text-field submit-button]]
        [centipair.validation :only [email-required password-required]]))



(defn validate-input []
  (.log js/console "Clicked me"))

(def login-form-data (atom {:username {:id "username" :label "Username"}
                            :password {:id "password" :label "Password"}
                            :button {:label "Login" :onclick validate-input}
                            }))




(def register-form-data (atom {:email {:id "email" :label "Email" :value "" :validation-required? true}
                               :password {:id "password" :label "Password" :value "" :validation-required? true}
                               :button {:label "Register"}
                               :centipair {:form-status "disabled"}}))


(defn register-user [data]
  (.log js/console data)
  )

(defn register-form [data owner]
  (reify
    om/IRender
    (render [this]
      (dom/form #js {:className "form-group" :role "form"}
                (om/build text-field data {:opts {:key :email  :validator email-required :text-type "text"}})
                (om/build text-field data {:opts {:key :password :validator password-required :text-type "password"}})
                (om/build submit-button data {:opts {:key :button :onclick register-user}})))))


(defn render-register-form []
  (om/root register-form register-form-data
           {:target (gdom/getElement "register-form")}))
