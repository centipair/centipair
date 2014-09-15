(ns centipair.core.db.connection
  (:require [clojurewerkz.cassaforte.client :as cc]
            [clojurewerkz.cassaforte.cql    :as cql]))



;; Will connect to localhost
(def conn (cc/connect ["127.0.0.1"]))


(defn dbcon []
  conn
  )
(cql/use-keyspace (dbcon) "core")

