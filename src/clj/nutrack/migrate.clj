(ns nutrack.migrate
  "Ensure that the database is migrated and seeded."
  (:require [clojure.java.jdbc :as jdbc]
            [migratus.core :as migratus])
  (:refer-clojure :exclude [ensure]))

(def database {:classname   "org.postgresql.Driver"
               :subprotocol "postgresql"
               :subname     "nutrack"
               :host        "127.0.0.1:5432"
               :user        "postgres"
               :password    "psql"
               :ssl         false})

(def config {:store :database
             :migration-dir "migrations/"
             :db database})

(defn ensure
  "Apply pending migrations and ensure database is seeded."
  []
  ;; (migratus/init config)
  (migratus/migrate config))
