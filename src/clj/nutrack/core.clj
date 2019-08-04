(ns nutrack.core
  (:require [yada.yada :as yada]
            [bidi.vhosts :refer [vhosts-model]]
            [cheshire.core :as json]))

(defn routes []
  ["/test"
   (yada/resource
    {:methods
     {:get
      {:produces "application/json"
       :response (json/generate-string {:response "Hello World"})}}})])

(defn start-server []
  (def server
    (yada/listener
     [""
      [["/" (yada/resource
            {:methods
             {:get
              {:produces "text/plain"
               :response "Hello Root."}}})]
       (routes)
       ["/api" (yada/swaggered (routes)
                        {:info {:title "Nutrack API"
                                :version "0.1"
                                :description "Describes how to get information from the backend server."}
                         :basePath "/api"})]
       ]]
     {:port 8080})))

(defn stop-server []
  ((:close server)))

(defn restart []
  (stop-server)
  (start-server))
