(ns nutrack.core
  (:require [yada.yada :as yada]
            [cheshire.core :as json]
            [iapetos.core :as prometheus]
            [iapetos.collector.jvm :as jvm]
            [iapetos.export :as export]))

(defonce registry
  (-> (prometheus/collector-registry)
      jvm/initialize))

(defn routes []
  [["/test"
     (yada/resource
      {:methods
       {:get
        {:produces "application/json"
         :response (json/generate-string {:response "Hello World"})}}})]
   ["/search"
    (yada/resource
     {:methods
      {:get
       {:produces "application/json"
        :response (json/generate-string [{:name "bread" :type "food" :text "Bread"}])}}})]])

(defn base []
  [["/" (yada/resource
         {:methods
          {:get
           {:produces "text/plain"
            :response "Hello Root."}}})]
   ["/api" (yada/swaggered ["" (routes)]
                           {:info {:title "Nutrack API"
                                   :version "0.1"
                                   :description "Describes how to get information from the backend server."}
                            :basePath "/api"})]
   ["/metrics" (yada/resource
                {:methods
                 {:get
                  {:produces "text/plain"
                   :response (export/text-format registry)}}})]
   ])

(defn start-server []
  (def server
    (yada/listener
     ["" (reduce conj (base) (routes))]
     {:port 8080})))

(defn stop-server []
  ((:close server)))

(defn restart []
  (stop-server)
  (start-server))

(defn -main
  "Main entrypoint for Uberjar"
  [& args]
  (start-server))
