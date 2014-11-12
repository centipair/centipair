(ns centipair.admin.menu
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [goog.dom :as gdom]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [enfocus.core :as ef]
            [secretary.core :as secretary :refer-macros [defroute]])
  (:require-macros [enfocus.macros :as em])
  (:import goog.History))


(def side-menu-items (atom [{:label "Dashboard" :icon "dashboard" :active false :url "/dashboard"}
                            {:label "Sites" :icon "globe" :active false :url "/sites"}]))

(defn deactivate [url item] 
  (if (= url (:url item)) 
    (assoc item :active true)
    (assoc item :active false)))


(defn activate-side-menu-item [url]
   (reset! side-menu-items (into [] (map (partial deactivate url) @side-menu-items))))


(defn render-side-menu []
  (om/root
   (fn [app owner]
     (om/component 
      (apply dom/ul #js {:className "nav nav-sidebar"}
             (map (fn [item] (dom/li #js{:className (if (:active item) "active")} 
                                     (dom/a #js {:href (str "#" (:url item))} 
                                            (dom/i #js {:className (str "fa fa-" (:icon item))}) " "
                                            (:label item)
                                            )))
                  app))))
   side-menu-items
   {:target (. js/document (getElementById "sidebar"))}))

(em/deftemplate view-dashboard "/admin/dashboard" [])

(defn show-page [page]
  (js/console.log page)
  (activate-side-menu-item page)
  (ef/at 
   ["#view"] (ef/content (view-dashboard))))


(secretary/set-config! :prefix "#")

(defroute "/dashboard" [] 
  (show-page "/dashboard")
  )
(defroute "/sites" [] (show-page "/sites"))

(doto (History.)
  (goog.events/listen
    EventType/NAVIGATE 
    #(em/wait-for-load (secretary/dispatch! (.-token %))))
  (.setEnabled true))

