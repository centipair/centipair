(ns centipair.news
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [goog.dom :as gdom]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [enfocus.core :as ef]
            [secretary.core :as secretary :refer-macros [defroute]]
            [centipair.admin.pages :as pages])
  (:require-macros [enfocus.macros :as em])
  (:import goog.History))


(defroute "/news" []
  (.log js/console "News Loaded"))

(defroute "/news/:page" {:as params}
  (js/console.log (str "News Page: " (:page params))))


(defroute "/category/:name/:page" {:as params}
  (js/console.log (str "Category News Page: " (:name params) " -- "  (:page params))))


(defroute "/read/:page-id" {:as params}
  (js/console.log (str "Read News : " (:page-id params))))



(doto (History.)
  (goog.events/listen
    EventType/NAVIGATE 
    #(em/wait-for-load (secretary/dispatch! (.-token %))))
  (.setEnabled true))
