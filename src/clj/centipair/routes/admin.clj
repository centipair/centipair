(ns centipair.routes.admin
  (:use compojure.core
        noir.util.route
        centipair.core.auth.user.forms
        ;;centipair.core.cms.forms
        centipair.core.auth.user.models
        ;;centipair.core.cms.models
        centipair.core.utilities.forms
        centipair.core.utilities.appresponse)
  (:require [centipair.core.views.layout :as layout]
            [noir.response :as response]
            [clojure.data.json :as json]
            [cheshire.core :refer :all]))

(defn admin-base [] (layout/render "admin.html"))

(defn dashboard []
  (layout/render "dashboard.html"))


(defn admin-access-denied []
  (send-status 403 "Access denied"))


(defn page [] "Page ")

(defn user-profile [] (layout/render "profile.html"))

(defn admin-help [] (layout/render "help.html"))

(defn admin-settings [] (layout/render "settings.html"))

(defn admin-business [] (layout/render "business.html"))

(defn admin-not-found [] "Page not found")

(def-restricted-routes admin-routes
  (GET "/admin-access-denied" [] (admin-access-denied))
  (GET "/admin/" [] (admin-base))
  (GET "/admin/dashboard" [] (dashboard))
  (GET "/admin/page" [] (page))
  (GET "/admin/profile" [] (user-profile))
  (GET "/admin/help" [] (admin-help))
  (GET "/admin/settings" [] (admin-settings))
  (GET "/admin/business" [] (admin-business))
  (GET "/admin/404" [] (admin-not-found))
  )
