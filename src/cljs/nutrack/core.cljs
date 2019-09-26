(ns nutrack.core
  (:require [reagent.core :as reagent]))

(enable-console-print!)

(println "alright!")

(defonce state (reagent/atom {}))

(defn header []
  [:header
   [:h3 "Nutrack"]])

(defn search []
  [:div
   [:input]])

(defn page []
  [:section.search
   [header]
   [search]])

(reagent/render [page] (.getElementById js/document "app"))
