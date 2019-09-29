(ns nutrack.core
  (:require [nutrack.svg :refer [angle-up angle-down sort-up sort-down]]
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
   ["Chicken","24g","0g","8g","2000g","£22.24"]
   ["Beef Mince","19.7g","0g","14.9g","900g","£4.50"]
   ["Blueberries","0.7g","14.5g","0.3g","150g","£2.00"]
   ["Oat Milk","1g","6.6g","2.8g","1000g","£1.80"]
   ["Whey","90g","2.5g","0.3g","125g","£1.75"]
   ["Egg","12.6g","0.4g","9g","400g","£1.60"]
   ["Chopped Tomatoes","1.3g","3.6g","0.2g","800g","£1.00"]
   ["Egg Noodles","4.7g","25.7g","0.5g","250g","£0.99"]
   ["Butter","0.6g","0.7g","82g","100g","£0.80"]])

(rf/reg-event-db
 :initialize
 (fn [_ _]
   {:tables {:ingredients {:header (first ingredient-table)
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
         [:div title (if open? angle-up angle-down)]
         [:div {:style {:max-height (if open? child-height 0)
                        :transition "max-height 0.8s"
                        :overflow "hidden"}}
          [:div
           {:ref #(when % ;; % contains the DOM node.
                    (swap! s assoc :child-height (.-clientHeight %)))}
           "This is a recipe to create Tikka Masala"]]]))))

(defn table [data]
  [:section.table
   (let [table @(rf/subscribe [::tables :ingredients])]
     [:table.shadow
      ^{:key :header}
      [:thead
       [:tr
        (for [h (:header table)]
          ^{:key h} [:th h])]]
      [:tbody
       (for [row (:rows table)]
         ^{:key (first row)}
         [:tr
          (for [v row]
            ^{:key (str (first row) "-" v)}
            [:td v])])]])])

(defn page [input]
  [:div.background
   [header]
   [search input]
   [table ingredient-table]
   [expandable-component :tikka-id "Tikka Masala Recipe"]
   [footer]])

(defonce _init (rf/dispatch-sync [:initialize]))
(reagent/render [page input] (.getElementById js/document "app"))
