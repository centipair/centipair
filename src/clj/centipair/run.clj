(ns centipair.run
  (:use centipair.handler)
  (:require [immutant.web :as web])
  (:gen-class))

(defn start-immutant-server [args]
  (web/run
    app
    {}))

(defn -main [& {:as args}]
  (start-immutant-server args))
