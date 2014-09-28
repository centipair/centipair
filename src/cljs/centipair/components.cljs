(ns centipair.components
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [goog.dom :as gdom]
            [cljs.core.async :refer [put! take! chan <!]])
  (:use [centipair.validation :only [default-validator]]))



(def notifier-state (atom {:class "notify" :text ""}))


(defn notifier [data owner]
  (om/component 
   (dom/div  #js {:className (:class data)} (:text data))))


(om/root
 notifier
 notifier-state
 {:target (gdom/getElement "om-notifier")})


(defn notify [code & [message]]
  (let [text (if (and (= code 102) (nil? message)) "Loading.." message)
        invalid-data-message (if (and (= code 422) (nil? message)) "Invalid data" message)]
    (case code
      200 (reset! notifier-state {:class "notify" :text ""})
      102 (reset! notifier-state {:class "notify notify-loading" :text text})
      404 (reset! notifier-state {:class "notify notify-error" :text "Not found"})
      500 (reset! notifier-state {:class "notify notify-error" :text "Internal server error"})
      422 (reset! notifier-state {:class "notify notify-error" :text invalid-data-message})
      (reset! notifier-state {:class "notify" :text ""})
      )))


(defn deduct-form-status [status-list]
  (if (> (reduce + (map #(if (= % true) 0 1) status-list)) 0)
    false
    true))

(defn handle-form-status
  [field]  
  (if (nil? (:validation-required? field))
    true
    (if (nil? (:status field))
      false 
      (if (= 200 (:status field))
        true
        false))))


(defn change-form-status
  [attrs]
  (let [form-status  (deduct-form-status (map handle-form-status (map second (into [] attrs))))]
    (if form-status
      (assoc attrs :centipair {:form-status ""})
      attrs
      )
    ))


(defn check-value
  [e attrs key validator]
  (let [value (.. e -target -value)]
    (do 
      (om/update! attrs (assoc @attrs key (validator (assoc (key @attrs) :value value))))
      (om/update! attrs (change-form-status @attrs))      )))


;;Form components
(def form-status {:422 "has-error"
                  :500 "has-error"
                  :200 ""})


(defn label [attrs owner {:keys [key validator] :as opts}]
  (reify 
    om/IRender 
    (render [this]
      (dom/label #js {:for (key attrs) :className "control-label"} (:label (key attrs))))))


(defn input [attrs owner {:keys [key validator text-type] :as opts}]
  (reify 
    om/IRender 
    (render [this]
      (dom/input #js {:type text-type
                      :id (:id (key attrs))
                      :placeholder (:placeholder (key attrs)) 
                      :className "form-control"
                      :onChange #(check-value % attrs key (or validator default-validator))
                      :onClick #(check-value % attrs key (or validator default-validator))
                      :value (:value (key attrs))
                      }))))


(defn text-field [attrs owner {:keys [key validator text-type] :as opts}]
  (reify
    om/IRender
    (render [this]
      (dom/div #js {:className (str "form-group " (form-status (keyword (str (str (:status (key attrs)))))))}
               (om/build label attrs {:opts {:key key :validator validator}})
             
               (om/build input attrs {:opts {:key key :validator validator :text-type text-type}})
               (dom/label #js {:className "control-label"} (:message (key attrs)))))))


(defn submit-button [attrs owner {:keys [key onclick] :as opts}]
  (reify 
    om/IRender 
    (render [this]
      (dom/button #js {:type "button"
                       :className "btn btn-primary"
                       :disabled (:form-status (:centipair attrs))
                       :onClick #(onclick attrs)}  
                  (:label (key attrs))))))


