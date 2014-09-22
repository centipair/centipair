(ns centipair.components
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [goog.dom :as gdom]
            [cljs.core.async :refer [put! take! chan <!]]))



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


(defn default-validator []
  (.log js/console "sdsdsd")
  )


(defn update-value [map]
  {:id "email" :label "Email changed"}
  )


(defn check-value
  [e attrs key validator]
  (let [value (.. e -target -value)]
  ;;(.log js/console (.. e -target -value))
  (om/update! attrs (assoc @attrs key (validator (assoc (key @attrs) :value value))))))

;;Form components

(def form-status {:422 "has-error"
                  :500 "has-error"
                  :200 ""})

(defn text-field-bkp [attrs]
  (let [status (str (:status attrs))]
    (dom/div #js {:className (str "form-group " (form-status (keyword (str status))))}
             (dom/label #js {:for (:id attrs) :className "control-label"} (:label attrs))
             (dom/input #js {:type "text"
                             :ref "new-contact" 
                             :id (:id attrs) 
                             :placeholder (:placeholder attrs) 
                             :className "form-control" 
                             :onClick (or (:validator attrs) default-validator)})
             (dom/label #js {:className "control-label"} (:message attrs)))))



(defn label [attrs owner {:keys [key validator] :as opts}]
  (reify 
    om/IRender 
    (render [this]
      (dom/label #js {:for (key attrs) :className "control-label"} (:label (key attrs)))
      )
    )
  )

(defn input [attrs owner {:keys [key validator text-type] :as opts}]
  
  (reify 
    om/IRender 
    (render [this]
      (dom/input #js {:type text-type
                      :id (:id (key attrs))
                      :placeholder (:placeholder (key attrs)) 
                      :className "form-control"
                      :onChange #(check-value % attrs key validator)
                      :onClick #(check-value % attrs key validator)
                      }))))


(defn text-field [attrs owner {:keys [key validator text-type] :as opts}]
  (reify
    om/IRender
    (render [this]
      (dom/div #js {:className (str "form-group " (form-status (keyword (str (str (:status (key attrs)))))))}
               (om/build label attrs {:opts {:key key :validator validator}})
             
               (om/build input attrs {:opts {:key key :validator validator :text-type text-type}})
               (dom/label #js {:className "control-label"} (:message (key attrs)))))))


(defn submit-button [attrs]
  (dom/button #js {:type "button" :className "btn btn-primary" :onClick (:onclick attrs)} (:label attrs) ))


