(ns centipair.admin.pages
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs.core.async :refer [put! chan <!]]))


(def page-state (atom [{:url "/" :title "Home Page" :id 1}
                  {:url "/about" :title "About Page" :id 2}
                  {:url "/contact" :title "Contact Page" :id 3}]))


(defn page-view [page owner]
  (reify
    om/IRenderState
    (render-state [this {:keys [delete]}]
      (dom/tr nil
        (dom/td nil (dom/a #js {:href "/page/edit/"} (:title page)))
        (dom/td nil (dom/i #js {:title "Delete"
                                :className "fa fa-trash clickable" 
                                :onClick (fn [e] (put! delete @page))}))))))



(defn pages-view [app owner]
  (reify
    om/IInitState
    (init-state [_]
      {:delete (chan)})
    om/IWillMount
    (will-mount [_]
      (let [delete (om/get-state owner :delete)]
        (go (loop []
              (let [page (<! delete)]
                (om/transact! app
                              (fn [xs] (js/console.log "deletign")(vec (remove #(= page %) xs))))
                (recur))))))
    om/IRenderState
    (render-state [this {:keys [delete]}]
      (dom/div nil
               (dom/h2 nil "Pages")
               (dom/table #js {:className "table"}
                          (apply dom/tbody nil
                                 (om/build-all page-view app
                                               {:init-state {:delete delete}})))))))

(defn render-pages-view [] (om/root pages-view page-state
                                    {:target (. js/document (getElementById "pages"))}))

(defn add-page []
  (swap! page-state conj {:url "/blog" :title "Blog" :id 4}))
