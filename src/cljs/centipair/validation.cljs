(ns centipair.validation)


(defn matches-regex?
  "Returns true if the string matches the given regular expression"
  [v regex]
  (boolean (re-matches regex v)))


(defn has-value?
  "Returns true if v is truthy and not an empty string."
  [v]
  (and v (not= v "")))


(defn not-nil?
  "Returns true if v is not nil"
  [v]
  (boolean (or v (false? v))))


(defn is-email?
  "Returns true if v is an email address"
  [v]
  (if (nil? v)
    false
    (matches-regex? v #"(?i)[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")))


(def required-field-error "This field is required")
(def email-field-error "Not a valid email address")

(defn validation-error [attrs message]
  (assoc attrs :status 422 :message message))

(defn valid-input [attrs]
  (assoc attrs :status 200 :message "")
  )

(defn email-required
  "Required email field"
  [attrs]
  (let [value (:value attrs)]
    (if (not-nil? value)
      (if (is-email? value)
        (valid-input attrs)
        (validation-error attrs email-field-error))
      (validation-error attrs required-field-error))))


(defn password-required [attrs]
  (let [value (:value attrs)]
    (if (not-nil? value)
      (valid-input attrs)
      (validation-error attrs required-field-error))))