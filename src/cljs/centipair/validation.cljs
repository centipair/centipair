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


(defn min-length?
  "Returns true if v is greater than or equal to the given len"
  [v len]
  (>= (count v) len))


;;error message for required field 
(def required-field-error "This field is required")

;;error message for email field
(def email-field-error "Not a valid email address")

;;error message for password length
(def password-length-error "Minimum 6 characters required")

(defn validation-error
  "Adds validation error to attributes when validation fails"
  [attrs message]
  (assoc attrs :status 422 :message message))

(defn valid-input
  "Cleans attributes when input is valid"
  [attrs]
  (assoc attrs :status 200 :message ""))

(defn email-required
  "Required email field validation"
  [attrs]
  (let [value (:value attrs)]
    (if (has-value? value)
      (if (is-email? value)
        (valid-input attrs)
        (validation-error attrs email-field-error))
      (validation-error attrs required-field-error))))


(defn password-required
  "Pasword required validation
  minimum password lenth is 6
  "
  [attrs]
  (let [value (:value attrs)]
    (if (has-value? value)
      (if (min-length? value 6)
        (valid-input attrs)
        (validation-error attrs password-length-error))
      (validation-error attrs required-field-error))))
