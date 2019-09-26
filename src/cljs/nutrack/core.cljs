(ns nutrack.core
  (:require [reagent.core :as reagent]))

(enable-console-print!)

(println "alright!")

(defonce state (reagent/atom {}))

(defn header []
  [:header
   [:h3 "Nutrack"]])

(def ingredients
  ["Steak"
   "Rice"
   "Chicken"
   "Lemon"
   "Pepper"])

(defn suggestions []
  [:div.suggestions
   [:ul
    (map (fn [e]
           ^{:key e} [:li e]) ingredients)]])

(defn search []
  [:section.search
   [:div
    [:input]
    [suggestions]]])

(defn page []
  [:div.background
   [header]
   [search]])

(reagent/render [page] (.getElementById js/document "app"))
