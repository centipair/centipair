(ns centipair.routes.news
  (:require [compojure.core :refer :all]
            [centipair.layout :as layout]
            [centipair.util :as util]
            [centipair.core.utilities.offline :as offline]))


(defn news-page []
  (layout/render "news.html"))


(defn news-appcache []
  (offline/news-cache))


(defroutes news-routes 
  (GET "/news" [] (news-page))
  (GET "/news.manifest" [] (news-appcache))
  )
