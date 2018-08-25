(ns cljs-counter-re.core
  (:require [re-frame.core :as rf]
            [reagent.core :as r]))

(rf/reg-event-db
  ::initialize-db
  (fn [_ _]
    {:model 0}))

(rf/reg-event-db
  ::increment
  (fn [db _]
    (update db :model inc)))

(rf/reg-event-db
  ::decrement
  (fn [db _]
    (update db :model dec)))

(rf/reg-sub
  ::model
  (fn [db _]
    (:model db)))

(defn main []
  [:div
   [:button {:on-click #(rf/dispatch [::decrement])} "-"]
   [:div @(rf/subscribe [::model])]
   [:button {:on-click #(rf/dispatch [::increment])} "+"]])

(rf/dispatch-sync [::initialize-db])
(r/render [main] (.getElementById js/document "app"))
