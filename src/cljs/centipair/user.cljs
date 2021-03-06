(ns centipair.user
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [goog.dom :as gdom])
  (:use [centipair.components :only [text-field submit-button]]
        [centipair.validation :only [email-required password-required]]
        [centipair.ajax :only [post]]))



(defn validate-input []
  (.log js/console "Clicked me"))

(def login-form-data (atom {:username {:id "username" :label "Username"}
                            :password {:id "password" :label "Password"}
                            :button {:label "Login" :onclick validate-input}}))




(def register-form-data (atom {:email {:id "email" :label "Email" :value "" :validation-required? true}
                               :password {:id "password" :label "Password" :value "" :validation-required? true}
                               :button {:label "Register"}
                               :centipair {:form-status "disabled"}}))


(defn handle-registration-success 
  [response] (.log js/console (:status-text response)))

(defn register-user
  [data]
  (post "/register-submit" 
        {:email (:value (:email data)) :password (:value (:password data))}
        handle-registration-success))


(defn register-form
  [data owner]
  (reify
    om/IRender
    (render [this]
      (dom/div #js {:className "form-group"}
                (om/build text-field data {:opts {:key :email  :validator email-required :text-type "text"}})
                (om/build text-field data {:opts {:key :password :validator password-required :text-type "password"}})
                (om/build submit-button data {:opts {:key :button :onclick register-user}})))))



(defn render-register-form []
  (om/root register-form register-form-data
           {:target (gdom/getElement "register-form")}))
