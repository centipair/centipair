(ns centipair.view
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [goog.dom :as gdom]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [enfocus.core :as ef]
            [secretary.core :as secretary :refer-macros [defroute]])
  (:require-macros [enfocus.macros :as em])
  (:import goog.History))


(em/deftemplate view-list "templates/news.html" [])
(em/deftemplate view-account "templates/account.html" [])

(defroute "/list" []
  (ef/at 
     ["#view"] (ef/content (view-list))))

(defroute "/account" []
  (ef/at 
     ["#view"] (ef/content (view-account))))


(doto (History.)
  (goog.events/listen
    EventType/NAVIGATE 
    #(em/wait-for-load (secretary/dispatch! (.-token %))))
  (.setEnabled true))

