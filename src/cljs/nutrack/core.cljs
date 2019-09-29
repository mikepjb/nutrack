(ns nutrack.core
  (:require [reagent.core :as reagent]
            [clojure.string :refer [includes? lower-case]]))

(enable-console-print!)

(println "alright!")

(defonce input (reagent/atom ""))

(defn header []
  [:header
   [:h3 "Nutrack"]])

(def angle-down
  [:svg {:viewBox "0 0 320 512", :xmlns "http://www.w3.org/2000/svg"}
 [:path
  {:d
     "M143 352.3L7 216.3c-9.4-9.4-9.4-24.6 0-33.9l22.6-22.6c9.4-9.4 24.6-9.4 33.9 0l96.4 96.4 96.4-96.4c9.4-9.4 24.6-9.4 33.9 0l22.6 22.6c9.4 9.4 9.4 24.6 0 33.9l-136 136c-9.2 9.4-24.4 9.4-33.8 0z"}]])

(def ingredients
  ["Steak"
   "Rice"
   "Chicken"
   "Lemon"
   "Pepper"])

(defn suggestions [input]
  [:div.suggestions
   [:ul
    (map (fn [e]
           ^{:key e} [:li e])
         (filter (fn [i] (includes?
                          (lower-case i)
                          (lower-case input))) ingredients))]])

(defn search [input]
  [:section.search
   [:div
    [:input {:value @input
             :on-change #(reset! input (-> % .-target .-value))}]
    [suggestions @input]]])

(defn expandable-component [title]
  (let [s (reagent/atom {:open false})]
    (fn [title]
      [:section.expandable {:on-click #(swap! s update :open not)}
       [:div title angle-down]
       (if (:open @s)
         [:div "hello"])])))

(defn page [input]
  [:div.background
   [header]
   [search input]
   [expandable-component "Tikka Masala Recipe"]])

(reagent/render [page input] (.getElementById js/document "app"))
