(ns centipair.routes.home
  (:require [compojure.core :refer :all]
            [centipair.layout :as layout]
            [centipair.util :as util]))

(defn home-page []
  (layout/render
    "home.html" {:content (util/md->html "/md/docs.md")}))

(defn about-page []
  (layout/render "about.html"))

(defn playground []
  (layout/render "playground.html"))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page))
  (GET "/playground" [] (playground)))
