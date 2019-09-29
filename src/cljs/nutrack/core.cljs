(ns nutrack.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]
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

(def angle-up
  [:svg {:viewBox "0 0 320 512", :xmlns "http://www.w3.org/2000/svg"}
 [:path
  {:d
     "M177 159.7l136 136c9.4 9.4 9.4 24.6 0 33.9l-22.6 22.6c-9.4 9.4-24.6 9.4-33.9 0L160 255.9l-96.4 96.4c-9.4 9.4-24.6 9.4-33.9 0L7 329.7c-9.4-9.4-9.4-24.6 0-33.9l136-136c9.4-9.5 24.6-9.5 34-.1z"}]])

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

(rf/reg-event-db
 :initialize
 (fn [_ _]
   {}))

(rf/reg-event-db
 :panel/toggle
 (fn [db [_ id]]
   (update-in db [:panels id] not)))

(rf/reg-sub
 :panel/state
 (fn [db [_ id]]
   (get-in db [:panels id])))

(defn expandable-component [id title]
  (let [s (reagent/atom {})] ;; will hold the child-height still..
    (fn [id title]
      (let [child-height (:child-height @s)
            open? @(rf/subscribe [:panel/state id])]
        [:section.expandable {:on-click #(rf/dispatch [:panel/toggle id])}
         [:div title (if open? angle-up angle-down)]
         [:div {:style {:max-height (if open? child-height 0)
                        :transition "max-height 0.8s"
                        :overflow "hidden"}}
          [:div
           {:ref #(when % ;; % contains the DOM node.
                    (swap! s assoc :child-height (.-clientHeight %)))}
           "This is a recipe to create Tikka Masala"]]]))))

(defn page [input]
  [:div.background
   [header]
   [search input]
   [expandable-component :tikka-id "Tikka Masala Recipe"]])

(reagent/render [page input] (.getElementById js/document "app"))
