(ns centipair.core.auth.user.forms
   (:use centipair.core.utilities.validators
         centipair.core.auth.user.models
         centipair.core.views.layout))


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
