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


(def side-menu-items (atom [{:id "dashboard" :label "Dashboard" :icon "dashboard" :active false :url "/dashboard"}
                            {:id "page":label "Page" :icon "globe" :active false :url "/page"}]))

(defn deactivate [id item] 
  (if (= id (:id item)) 
    (assoc item :active true)
    (assoc item :active false)))


(defn activate-side-menu-item [id]
   (reset! side-menu-items (into [] (map (partial deactivate id) @side-menu-items))))


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
(em/deftemplate view-pages "/admin/page" [])
(em/deftemplate view-help "/admin/help" [])

(def templates {:dashboard view-dashboard
                :pages view-pages})

(defn show-page [page]
  (let [template-function ((keyword page) templates)]
    (activate-side-menu-item page)
    (ef/at 
     ["#view"] (ef/content (template-function)))))


(secretary/set-config! :prefix "#")

(defroute "/dashboard" []
  (show-page "dashboard")
  )
(defroute "/page" [] (show-page "page"))

(doto (History.)
  (goog.events/listen
    EventType/NAVIGATE 
    #(em/wait-for-load (secretary/dispatch! (.-token %))))
  (.setEnabled true))

