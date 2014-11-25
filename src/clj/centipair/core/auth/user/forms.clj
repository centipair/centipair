(ns centipair.core.auth.user.forms
   (:use centipair.core.utilities.validators
         centipair.core.auth.user.models
         centipair.core.views.layout
         noir.validation)
   (:require [validateur.validation :refer :all]))


(defn email-exists? [value & message]
  (if (map-or-nil? value)
    value
    (if (nil? (select-user-email value))
      value
      (email-exists-failed message))))

(defn email-should-exist? [value & message]
  (if (map-or-nil? value)
    value
    (if (nil? (select-user-email value))
      (email-should-exist-failed message)
      value)))

(defn username-exists? [value & message]
  (if (map-or-nil? value)
    value
    (if (nil? (select-user-username value))
      value
      (username-exists-failed message))))


(defn register-form [form]
  (validate form 
            [:username required? username? username-exists?]
            [:password required?]
            [:email required? email? email-exists?]
            [:tos [required? "You have to agree"]]))


(defn login-form [form]
  (validate form
   [:username [required? "Username is required"]]
   [:password [required? "Password is required"]]))

(defn early-access-check [form]
  (validate form
   [:email required? email?]
   ))




(defn forgot-password-form-validation [form]
  (validate form [:email required? email? email-should-exist?]))


(defn email-exist-check [value]
  (if (has-value? value)
    (if (nil? (select-user-email value))
      true
      false)))


(def registration-validator
  (validation-set
   (presence-of :email :message "Your email address is required for registration")
   (presence-of :password :message "Please choose a password")
   (validate-by :email email-exist-check :message "This email already exists")))


(defn user-registration-form [params]
  (let [validation-result (registration-validator params)]
    (if (valid? validation-result)
      (register-user (assoc params :username (:email params)))
      validation-result)))


(def login-validator
  (validation-set
   (presence-of :username :message "Please enter the email address you have registered.")
   (presence-of :password :message "Please enter your password")))


(defn user-login-form [params]
  (let [validation-result (login-validator params)]
    (if (valid? validation-result)
      (login params)
      validation-result)))
