(ns mori.async
  (:refer-clojure :exclude [reduce split pipe into merge map take put partition partition-by])
  (:require-macros
   [mori.macros :refer [mori-export make-inspectable]])
  (:require [cljs.core.async :as async]
            [goog.Promise]))

(mori-export async.chan async/chan)
(mori-export async.toChan async/to-chan)
(mori-export async.ontoChan async/onto-chan)
(mori-export async.take$ async/take!)

(defn ^:export put [chan val]
  (goog/Promise. (fn [resolve, reject] (async/put! chan val (fn [res] (resolve res))))))

(defn ^:export take [chan]
  (goog/Promise. (fn [resolve, reject] (async/take! chan (fn [res] (resolve res))))))

(defn ^:export alts [chans]
  (goog/Promise. (fn [resolve, reject] (async/do-alts (fn [res] (resolve res)) chans))))

(mori-export async.put$ async/put!)
(mori-export async.timeout async/timeout)
(mori-export async.promiseChan async/promise-chan)
(mori-export async.close$ async/close!)
(mori-export async.pipelineAync async/pipeline-async)
(mori-export async.pipeline async/pipeline)
(mori-export async.pipe async/pipe)
(mori-export async.into async/into)
(mori-export async.split async/split)
(mori-export async.reduce async/reduce)
(mori-export async.merge async/merge)
(mori-export async.map async/map)
(mori-export async.partition async/partition)
(mori-export async.partitionBy async/partition-by)
(mori-export async.doAlts async/do-alts)
(mori-export async.offer$ async/offer!)
(mori-export async.pull$ async/poll!)
(mori-export async.pub async/pub)
(mori-export async.sub async/sub)
(mori-export async.unsub async/unsub)
(mori-export async.unsubAll async/unsub-all)
(mori-export async.mult async/mult)
(mori-export async.tap async/tap)
(mori-export async.untap async/untap)
(mori-export async.untapAll async/untap-all)
(mori-export async.mix async/mix)
(mori-export async.admix async/admix)
(mori-export async.unmix async/unmix)
(mori-export async.unmixAll async/unmix-all)
(mori-export async.toggle async/toggle)
(mori-export async.soloMode async/solo-mode)