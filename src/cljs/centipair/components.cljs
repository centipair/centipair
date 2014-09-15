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


;;Form components

(def form-status {:422 "has-error"
                  :500 "has-error"
                  :200 ""})

(defn text-field [attrs]
  (let [status (str (:status attrs))]
    (dom/div #js {:className (str "form-group " (form-status (keyword (str status))))}
             (dom/label #js {:for (:id attrs) :className "control-label"} (:label attrs))
             (dom/input #js {:type "text" :ref "new-contact" :id (:id attrs) :placeholder (:placeholder attrs) :className "form-control" })
             (dom/label #js {:className "control-label"} (:message attrs)))))
