(ns centipair.core.utilities.mail
  (:use postal.core
        centipair.core.settings
        centipair.core.secret
        selmer.parser))

;;email settings should be defined in centipair.core.secret
;;(def email-settings {:host "smtphost.server.com"
;;                     :user "username"
;;                     :pass "password"
;;                     :port 123})
;;

(defn send-registration-email [registration-request]
  (let [email-body (render-file "templates/email/registration.html" 
                                (merge site {:registration-key (str (:registration_key registration-request))}))]
    (send-message email-settings
                  {:from (:site_email site)
                   :to (:email registration-request)
                   :subject (str "Please activate your " (:site_name site) " account")
                   :body [{:type "text/html"
                           :content email-body}]}
                  )))

(defn send-password-reset-email [email password-reset]
  (let [email-body (render-file "centipair/core/views/templates/email/password-reset.html" 
                                (merge site {:reset-key (str (:password_reset_key password-reset))}))]
    (send-message email-settings
                  {:from (:site_email site)
                   :to email
                   :subject (str "Reset your " (:site_name site) " password")
                   :body [{:type "text/html"
                           :content email-body}]})))
