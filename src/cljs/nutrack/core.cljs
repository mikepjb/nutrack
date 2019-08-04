(ns nutrack.core
  (:require [reagent.core :as reagent]))

(enable-console-print!)

(println "alright!")

(defonce state (reagent/atom {}))

(defn title []
  [:h3 "Nutrack"])

(reagent/render [title] (.getElementById js/document "app"))
