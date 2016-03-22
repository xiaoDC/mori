(ns mori.async
  (:refer-clojure :exclude [reduce split pipe into merge map take put partition partition-by atom swap!])
  (:require-macros
   [mori.macros :refer [make-inspectable property-export]]
   [cljs.core.async.macros :refer [go-loop]])
  (:require [cljs.core.async :as async]
            [goog.Promise]))

(def ^:export atom cljs.core/atom)
(def ^:export swap cljs.core/swap!)

(def ^:export chan async/chan)
(def ^:export toChan async/to-chan)
(def ^:export ontoChan async/onto-chan)
(def ^:export take$ async/take!)

(defn ^:export observe [chan cb]
  (go-loop []
    (let [v (async/<! chan)]
      (cb v)
      (recur))))

(defn ^:export go
  ([f] (let [gen (f) next (.next gen)] (go gen next)))
  ([gen next] (cond
                (.-done next) (.-value next)
                (aget (.-value next) "then") (.then (.-value next) (fn [val] (go gen (.next gen val))))
                :else (go gen (.next gen (.-value next))))))

(defn ^:export put [chan val]
  (goog/Promise. (fn [resolve, reject] (async/put! chan val (fn [res] (resolve res))))))

(defn ^:export take [chan]
  (goog/Promise. (fn [resolve, reject] (async/take! chan (fn [res] (resolve res))))))

(defn ^:export alts [chans opts]
  (goog/Promise. (fn [resolve, reject] (async/do-alts (fn [res] (resolve res)) chans opts))))

(def ^:export put$ async/put!)
(def ^:export timeout async/timeout)
(def ^:export promiseChan async/promise-chan)
(def ^:export close$ async/close!)
(def ^:export pipelineAync async/pipeline-async)
(def ^:export pipeline async/pipeline)
(def ^:export pipe async/pipe)
(def ^:export into async/into)
(def ^:export split async/split)
(def ^:export reduce async/reduce)
(def ^:export merge async/merge)
(def ^:export map async/map)
(def ^:export partition async/partition)
(def ^:export partitionBy async/partition-by)
(def ^:export doAlts async/do-alts)
(def ^:export offer$ async/offer!)
(def ^:export pull$ async/poll!)
(def ^:export pub async/pub)
(def ^:export sub async/sub)
(def ^:export unsub async/unsub)
(def ^:export unsubAll async/unsub-all)
(def ^:export mult async/mult)
(def ^:export tap async/tap)
(def ^:export untap async/untap)
(def ^:export untapAll async/untap-all)
(def ^:export mix async/mix)
(def ^:export admix async/admix)
(def ^:export unmix async/unmix)
(def ^:export unmixAll async/unmix-all)
(def ^:export toggle async/toggle)
(def ^:export soloMode async/solo-mode)
(def ^:export ManyToManyChannel cljs.core.async.impl.channels/ManyToManyChannel)
(property-export "async.ManyToManyChannel.prototype.take$" async/take!)
(property-export "async.ManyToManyChannel.prototype.put$" async/put!)
(property-export "async.ManyToManyChannel.prototype.take" take)
(property-export "async.ManyToManyChannel.prototype.put" put)
(property-export "async.ManyToManyChannel.prototype.close$" async/close!)
