(ns centipair.user
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [goog.dom :as gdom])
  (:use [centipair.components :only [text-field]]))


(def login-form-data (atom {:username {:id "username" :label "Username"}
                            :password {:id "password" :label "Password"}}))


(defn login-form [data owner]
  (reify
    om/IRender
    (render [this]
      (dom/form #js {:className "form-group" :role "form"}
                (text-field (:username data))
                (text-field (:password data))))))


(defn render-login-form []
  (om/root login-form login-form-data
           {:target (gdom/getElement "login_form")}))



(def register-form-data (atom {:email {:id "email" :label "Email"}
                               :password {:id "password" :label "Password"}}))
