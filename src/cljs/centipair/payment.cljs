(ns centipair.payment
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [goog.dom :as gdom])
  (:use [centipair.components :only [text-field submit-button]]
        [centipair.validation :only [required]]))


(def payment-form-data (atom {:cc-number {:id "cc-number" :label "Credit card number" :validation-required? true}
                              :cvv {:id "cvv" :label "Security Code" :validation-required? true}
                              :button {:label "Pay"}
                              :centipair {:form-status "disabled"}}))


(defn payment-form [data owner]
   (reify
    om/IRender
    (render [this]
      (dom/form #js {:className "form-group" :role "form"}
                (om/build text-field data {:opts {:key :cc-number  :validator required :text-type "text"}})
                (om/build text-field data {:opts {:key :cvv :validator required :text-type "password"}})
                (om/build submit-button data {:opts {:key :button}})))))



(defn render-payment-form []
  (om/root payment-form payment-form-data
           {:target (gdom/getElement "payment-form")}))

