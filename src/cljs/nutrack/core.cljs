(ns nutrack.core
  (:require [nutrack.svg :as svg]
            [reagent.core :as reagent]
            [re-frame.core :as rf]
            [clojure.string :refer [includes? lower-case]]))

(enable-console-print!)

(println "alright!")

(defonce input (reagent/atom ""))

(defn header []
  [:header
   [:h3 "Nutrack"]])

(defn footer []
  [:footer
   [:h5 "Michael Bruce ©"]])

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

(def ingredient-table
  [["Name" "Protein" "Carbs" "Fat" "Amount" "Cost"]
   ["Chicken" 24 0 8 2000 22.24]
   ["Beef Mince" 19.7 0 14.9 900 4.50]
   ["Blueberries" 0.7 14.5 0.3 150 2.00]
   ["Oat Milk" 1 6.6 2.8 1000 1.80]
   ["Whey" 90 2.5 0.3 125 1.75]
   ["Egg" 12.6 0.4 9 400 1.60]
   ["Chopped Tomatoes" 1.3 3.6 0.2 800 1.00]
   ["Egg Noodles" 4.7 25.7 0.5 250 0.99]
   ["Butter" 0.6 0.7 82 100 0.80]])

(rf/reg-event-db
 :initialize
 (fn [_ _]
   {:tables {:ingredient {:header (first ingredient-table)
                          :rows (vec (rest ingredient-table))}}}))

(rf/reg-event-db
 :panel/toggle
 (fn [db [_ id]]
   (update-in db [:panels id] not)))

(rf/reg-sub
 :panel/state
 (fn [db [_ id]]
   (get-in db [:panels id])))

(rf/reg-sub
 ::tables
 (fn [db [_ key]]
   (get-in db [:tables key])))

(defn expandable-component [id title]
  (let [s (reagent/atom {})] ;; will hold the child-height still..
    (fn [id title]
      (let [child-height (:child-height @s)
            open? @(rf/subscribe [:panel/state id])]
        [:section.expandable {:on-click #(rf/dispatch [:panel/toggle id])}
         [:div title (if open? svg/angle-up svg/angle-down)]
         [:div {:style {:max-height (if open? child-height 0)
                        :transition "max-height 0.8s"
                        :overflow "hidden"}}
          [:div
           {:ref #(when % ;; % contains the DOM node.
                    (swap! s assoc :child-height (.-clientHeight %)))}
           "This is a recipe to create Tikka Masala"]]]))))

(defn cycle-direction [old-i new-i direction]
  (if (= old-i new-i)
    (if (= direction :descending) :ascending :descending)
    (if (= new-i 0) ;; if name column selected
      :descending :ascending)))

(defn table [table-key]
  (let [s (reagent/atom {})]
    (fn [table-key]
      [:section.table
       (let [table @(rf/subscribe [::tables table-key])
             key (:sort-key @s)
             rows (cond->> (:rows table)
                    key (sort-by #(nth % key))
                    (= :ascending (:sort-direction @s)) reverse)]
         [:table.shadow
          ^{:key :header}
          [:thead
           [:tr
            (for [[i h] (map vector (range) (:header table))]
              ^{:key h} [:th {:on-click #(swap! s assoc
                                                :sort-key i
                                                :sort-direction (cycle-direction
                                                                 (:sort-key @s)i (:sort-direction @s)))}
                         h svg/sort])]]
          [:tbody
           (for [row rows]
             ^{:key (first row)}
             [:tr
              (for [v row]
                ^{:key (str (first row) "-" v)}
                [:td v])])]])])))

(defn page [input]
  [:div.background
   [header]
   [search input]
   [table :ingredient]
   [expandable-component :tikka-id "Tikka Masala Recipe"]
   [footer]])

(defonce _init (rf/dispatch-sync [:initialize]))
(reagent/render [page input] (.getElementById js/document "app"))
