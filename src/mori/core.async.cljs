(ns mori.async
  (:refer-clojure :exclude [reduce split pipe into merge map take put partition partition-by])
  (:require-macros
   [mori.macros :refer [mori-export make-inspectable]])
  (:require [cljs.core.async :as async]
            [goog.Promise]))

(mori-export chan async/chan)
(mori-export toChan async/to-chan)
(mori-export ontoChan async/onto-chan)
(mori-export take$ async/take!)

(defn ^:export put [chan val]
  (goog/Promise. (fn [resolve, reject] (async/put! chan val (fn [res] (resolve res))))))

(defn ^:export take [chan]
  (goog/Promise. (fn [resolve, reject] (async/take! chan (fn [res] (resolve res))))))

(defn ^:export alts [chans]
  (goog/Promise. (fn [resolve, reject] (async/do-alts (fn [res] (resolve res)) chans))))

(mori-export put$ async/put!)
(mori-export timeout async/timeout)
(mori-export promiseChan async/promise-chan)
(mori-export close$ async/close!)
(mori-export pipelineAync async/pipeline-async)
(mori-export pipeline async/pipeline)
(mori-export pipe async/pipe)
(mori-export into async/into)
(mori-export split async/split)
(mori-export reduce async/reduce)
(mori-export merge async/merge)
(mori-export partition async/partition)
(mori-export partitionBy async/partition-by)
(mori-export doAlts async/do-alts)
(mori-export offer$ async/offer!)
(mori-export pull$ async/poll!)
(mori-export pub async/pub)
(mori-export sub async/sub)
(mori-export unsub async/unsub)
(mori-export unsubAll async/unsub-all)
(mori-export mult async/mult)
(mori-export tap async/tap)
(mori-export untap async/untap)
(mori-export untapAll async/untap-all)
(mori-export mix async/mix)
(mori-export admix async/admix)
(mori-export unmix async/unmix)
(mori-export unmixAll async/unmix-all)
(mori-export toggle async/toggle)
(mori-export soloMode async/solo-mode)

(mori-export map cljs.core/map)
(mori-export mapcat cljs.core/mapcat)
(mori-export reduce cljs.core/reduce)
(mori-export keep cljs.core/keep)
(mori-export filter cljs.core/filter)
(mori-export remove cljs.core/remove)
(mori-export some cljs.core/some)
(mori-export equals cljs.core/=)
(mori-export repeat cljs.core/repeat)
