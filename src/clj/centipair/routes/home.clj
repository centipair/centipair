(ns centipair.routes.home
  (:use centipair.core.auth.user.models
        centipair.core.auth.user.forms
        centipair.core.cryptography
        centipair.core.utilities.appresponse)
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

(defn register-submit [request]
  (send-response (user-registration-form (:params request))))

(defn activate [registration-key]
  (let [user-id (activate-account (str-uuid registration-key))]
  (if user-id
    (do
      (layout/render "login.html" {:title "Account activated" :message "Your account has been activated.Please Login"})
    )
    (layout/render "account-activation.html" {:title "Account activation error" :message "Invalid activation code"}))))


(defn playground []
  (layout/render "playground.html"))

(defn check-email [request]
  (str request))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page))
  (GET "/playground" [] (playground))
  (GET "/register" [] (register-page))
  (POST "/register-submit" request (register-submit request))
  (GET "/activate/:key" [key] (activate key))
  (GET "/check-email" request (check-email request)))
