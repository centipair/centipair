(ns centipair.routes.home
  (:require [compojure.core :refer :all]
            [centipair.layout :as layout]
            [centipair.util :as util]))

(defn home-page []
  (layout/render
    "home.html" {:content (util/md->html "/md/docs.md")}))

(defn about-page []
  (layout/render "about.html"))


(defn register-page []
  (layout/render "register.html"))


(defn playground []
  (layout/render "playground.html"))

(defn check-email [request]
  (str request)
  )
(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page))
  (GET "/playground" [] (playground))
  (GET "/register" [] (register-page))
  (GET "/check-email" request (check-email request)))
