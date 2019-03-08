(ns cljs-counter-re.core
  (:require [re-frame.core :as rf]
            [reagent.core :as r]))

(rf/reg-event-db
  ::initialize-db
  (fn [_ _]
    {:model 0}))

(rf/reg-event-db
  ::increment
  (fn [db [_ n]]
    (update db :model + n)))

(rf/reg-event-db
  ::decrement
  (fn [db [_ n]]
    (update db :model - n)))

(rf/reg-cofx
  ::random-n
  (fn [cofx _]
    (assoc cofx :n (inc (rand-int 5)))))

(rf/reg-event-fx
  ::increment-n
  [(rf/inject-cofx ::random-n)]
  (fn [{:keys [n]} _]
    {:dispatch [::increment n]}))

(rf/reg-event-fx
  ::decrement-n
  [(rf/inject-cofx ::random-n)]
  (fn [{:keys [n]} _]
    {:dispatch [::decrement n]}))

(rf/reg-sub
  ::model
  (fn [db _]
    (:model db)))

(defn main []
  [:div
   [:button {:on-click #(rf/dispatch [::decrement-n])} "-"]
   [:div @(rf/subscribe [::model])]
   [:button {:on-click #(rf/dispatch [::increment-n])} "+"]])

(rf/dispatch-sync [::initialize-db])
(r/render [main] (.getElementById js/document "app"))
