(ns mori
  (:require-macros
   [mori.macros :refer [make-inspectable property-export]])
  (:refer-clojure :exclude
   [count empty conj find nth assoc dissoc disj pop peek get
    empty? reverse into merge subvec keys vals
    equiv sort sort-by
    vector vec array-map hash-map set compare distinct first second next rest seq cons last
    get-in update-in assoc-in fnil
    take drop take-nth partition partition-all partition-by iterate
    merge-with
    take-while drop-while group-by
    interpose interleave concat flatten
    prim-seq lazy-seq keep keep-indexed
    map mapcat map-indexed reduce reduce-kv filter remove some every?
    transduce eduction sequence dedupe completing
    repeat repeatedly
    partial comp juxt
    identity constantly
    zipmap sorted-set keyword symbol
    sorted-set-by sorted-map sorted-map-by
    sum inc dec even? odd? subseq
    meta with-meta vary-meta
    apply hash select-keys into-array list range memoize
    rand trampoline]))

(def ^:export equiv cljs.core/==)
(def ^:export partial cljs.core/partial)
(def ^:export comp cljs.core/comp)
(def ^:export keyword cljs.core/keyword)

(defn ^:export pipeline [& args]
  (cljs.core/reduce #(%2 %1) args))

(defn ^:export curry [fun & args]
  (fn [arg]
    (cljs.core/apply fun (cljs.core/cons arg args))))

(def ^:export juxt cljs.core/juxt)

(defn ^:export knit [& fns]
  (fn [args]
    (cljs.core/into-array (cljs.core/map #(% %2) fns args))))

;; Useful fns

(def ^:export sum cljs.core/+)
(def ^:export inc cljs.core/inc)
(def ^:export dec cljs.core/dec)
(def ^:export isEven cljs.core/even?)
(def ^:export isOdd cljs.core/odd?)

(defn ^:export each [xs f]
  (doseq [x xs]
    (f x)))

(def ^:export memoize cljs.core/memoize)
(def ^:export rand cljs.core/rand)
(def ^:export identity cljs.core/identity)
(def ^:export constantly cljs.core/constantly)
(def ^:export compare cljs.core/compare)
(def ^:export toJs cljs.core/clj->js)
(defn ^:export toClj
  ([x] (cljs.core/js->clj x))
  ([x keywordize-keys] (cljs.core/js->clj x :keywordize-keys keywordize-keys)))

;; ;; transducers

(def ^:export transduce cljs.core/transduce)
(def ^:export eduction cljs.core/eduction)
(def ^:export sequence cljs.core/sequence)
(def ^:export completing cljs.core/completing)

(def ^:export trampoline cljs.core/trampoline)

;; Predicates
(def ^:export isList cljs.core/list?)
(def ^:export isSeq cljs.core/seq?)
(def ^:export isVector cljs.core/vector?)
(def ^:export isMap cljs.core/map?)
(def ^:export isSet cljs.core/set?)

(def ^:export isKeyword cljs.core/keyword?)
(def ^:export isSymbol cljs.core/symbol?)

(def ^:export isCollection cljs.core/coll?)
(def ^:export isSequential cljs.core/sequential?)
(def ^:export isAssociative cljs.core/associative?)
(def ^:export isCounted cljs.core/counted?)
(def ^:export isIndexed cljs.core/indexed?)
(def ^:export isReduceable cljs.core/reduceable?)
(def ^:export isSeqable cljs.core/seqable?)
(def ^:export isReversible cljs.core/reversible?)

(def ^:export notEquals cljs.core/not=)
(def ^:export gt cljs.core/>)
(def ^:export gte cljs.core/>=)
(def ^:export lt cljs.core/<)
(def ^:export lte cljs.core/<=)

(defn sequential-or-array? [x]
  (or (array? x)
      (sequential? x)))

(defn ^:export flatten [x]
  (cljs.core/filter #(not (sequential-or-array? %))
    (cljs.core/rest (tree-seq sequential-or-array? cljs.core/seq x))))

; The real lazy-seq is a macro, but it just expands its body into a function
(defn ^:export lazySeq [f]
  (new cljs.core/LazySeq nil f nil nil))

(def ^:export queue (fn [& args] (cljs.core/into cljs.core.PersistentQueue.EMPTY args)))

(def ^:export range cljs.core/range)
(def ^:export list cljs.core/list)
(def ^:export intoArray cljs.core/into-array)
(def ^:export selectKeys cljs.core/select-keys)
(def ^:export hash cljs.core/hash)
(def ^:export apply cljs.core/apply)
(def ^:export distinct cljs.core/distinct)
(def ^:export first cljs.core/first)
(def ^:export second cljs.core/second)
(def ^:export next cljs.core/next)
(def ^:export rest cljs.core/rest)
(def ^:export seq cljs.core/seq)
(def ^:export cons cljs.core/cons)
(def ^:export last cljs.core/last)
(def ^:export getIn cljs.core/get-in)
(def ^:export updateIn cljs.core/update-in)
(def ^:export assocIn cljs.core/assoc-in)
(def ^:export fnil cljs.core/fnil)
(def ^:export hasKey cljs.core/contains?)
(def ^:export take cljs.core/take)
(def ^:export drop cljs.core/drop)
(def ^:export takeNth cljs.core/take-nth)
(def ^:export partition cljs.core/partition)
(def ^:export partitionAll cljs.core/partition-all)
(def ^:export partitionBy cljs.core/partition-by)
(def ^:export iterate cljs.core/iterate)
(def ^:export mergeWith cljs.core/merge-with)
(def ^:export takeWhile cljs.core/take-while)
(def ^:export dropWhile cljs.core/drop-while)
(def ^:export groupBy cljs.core/group-by)
(def ^:export interpose cljs.core/interpose)
(def ^:export interleave cljs.core/interleave)
(def ^:export concat cljs.core/concat)

(def ^:export count cljs.core/count)
(def ^:export empty cljs.core/empty)
(def ^:export conj cljs.core/conj)
(def ^:export into cljs.core/into)
(def ^:export map cljs.core/map)
(def ^:export reduce cljs.core/reduce)
(def ^:export filter cljs.core/filter)
(def ^:export find cljs.core/find)
(def ^:export nth cljs.core/nth)
(def ^:export assoc cljs.core/assoc)
(def ^:export dissoc cljs.core/dissoc)
(def ^:export disj cljs.core/disj)
(def ^:export pop cljs.core/pop)
(def ^:export peek cljs.core/peek)
(def ^:export get cljs.core/get)
(def ^:export isEmpty cljs.core/empty?)
(def ^:export zipmap cljs.core/zipmap)
(def ^:export reverse cljs.core/reverse)
(def ^:export merge cljs.core/merge)
(def ^:export subvec cljs.core/subvec)
(def ^:export keys cljs.core/keys)
(def ^:export vals cljs.core/vals)
(def ^:export sort cljs.core/sort)
(def ^:export sortBy cljs.core/sort-by)
(def ^:export vector cljs.core/vector)
(def ^:export vec cljs.core/vec)
(def ^:export remove cljs.core/remove)
(def ^:export some cljs.core/some)
(def ^:export every cljs.core/every?)
(def ^:export equals cljs.core/=)
(def ^:export repeat cljs.core/repeat)
(def ^:export repeatedly cljs.core/repeatedly)
(def ^:export subseq cljs.core/subseq)
(def ^:export dedupe cljs.core/dedupe)

(def ^:export Vector cljs.core/PersistentVector)

(property-export "Vector.prototype.nth" cljs.core/nth)
(property-export "Vector.prototype.peek" cljs.core/peek)
(property-export "Vector.prototype.pop" cljs.core/pop)
(property-export "Vector.prototype.conj" cljs.core/conj)
(property-export "Vector.prototype.equiv" cljs.core/==)
(property-export "Vector.prototype.empty" cljs.core/empty)
(property-export "Vector.prototype.count" cljs.core/count)
(property-export "Vector.prototype.get" cljs.core/get)
(property-export "Vector.prototype.key" cljs.core/key)
(property-export "Vector.prototype.val" cljs.core/val)
(property-export "Vector.prototype.assoc" cljs.core/assoc)
(property-export "Vector.prototype.reduce" (fn [coll f init] (cljs.core/reduce f init coll)))
(property-export "Vector.prototype.kvReduce" (fn [coll f init] (cljs.core/reduce-kv f init coll)))
(property-export "Vector.prototype.asTransient" cljs.core/transient)

(def ^:export hashMap cljs.core/array-map)
(def ^:export ArrayMap cljs.core/PersistentArrayMap)
(property-export "ArrayMap.prototype.conj" cljs.core/conj)
(property-export "ArrayMap.prototype.empty" cljs.core/empty)
(property-export "ArrayMap.prototype.count" cljs.core/count)
(property-export "ArrayMap.prototype.assoc" cljs.core/assoc)
(property-export "ArrayMap.prototype.dissoc" cljs.core/dissoc)
(property-export "ArrayMap.prototype.reduce" (fn [coll f init] (cljs.core/reduce f init coll)))
(property-export "ArrayMap.prototype.kvReduce" (fn [coll f init] (cljs.core/reduce-kv f init coll)))
(property-export "ArrayMap.prototype.asTransient" cljs.core/transient)

(goog/exportSymbol "mori.ArrayMap.prototype.get" (.. cljs.core/PersistentArrayMap -prototype -get))
(goog/exportSymbol "mori.ArrayMap.prototype.has" (.. cljs.core/PersistentArrayMap -prototype -has))
(goog/exportSymbol "mori.ArrayMap.prototype.equiv" (.. cljs.core/PersistentArrayMap -prototype -equiv))
(goog/exportSymbol "mori.ArrayMap.prototype.keys" (.. cljs.core/PersistentArrayMap -prototype -keys))
(goog/exportSymbol "mori.ArrayMap.prototype.entries" (.. cljs.core/PersistentArrayMap -prototype -entries))
(goog/exportSymbol "mori.ArrayMap.prototype.values" (.. cljs.core/PersistentArrayMap -prototype -values))
(goog/exportSymbol "mori.ArrayMap.prototype.forEach" (.. cljs.core/PersistentArrayMap -prototype -forEach))

(defn ^:export arrayMapFromArray [arr no-clone no-check]
  (cljs.core/PersistentArrayMap.fromArray arr no-clone no-check))

(defn ^:export arrayMapUnwrap [m]
  (if (instance? cljs.core/PersistentArrayMap m)
    (.-arr m)
    (throw (js/Error. "Can only unwrap array maps"))))

(def ^:export Map cljs.core/PersistentHashMap)
(property-export "Map.prototype.conj" cljs.core/conj)
(property-export "Map.prototype.empty" cljs.core/empty)
(property-export "Map.prototype.count" cljs.core/count)
(property-export "Map.prototype.key" cljs.core/key)
(property-export "Map.prototype.val" cljs.core/val)
(property-export "Map.prototype.assoc" cljs.core/assoc)
(property-export "Map.prototype.dissoc" cljs.core/dissoc)
(property-export "Map.prototype.reduce" (fn [coll f init] (cljs.core/reduce f init coll)))
(property-export "Map.prototype.kvReduce" (fn [coll f init] (cljs.core/reduce-kv f init coll)))
(property-export "Map.prototype.asTransient" cljs.core/transient)


(goog/exportSymbol "mori.Map.prototype.get" (.. cljs.core/PersistentHashMap -prototype -get))
(goog/exportSymbol "mori.Map.prototype.has" (.. cljs.core/PersistentHashMap -prototype -has))
(goog/exportSymbol "mori.Map.prototype.equiv" (.. cljs.core/PersistentHashMap -prototype -equiv))
(goog/exportSymbol "mori.Map.prototype.keys" (.. cljs.core/PersistentHashMap -prototype -keys))
(goog/exportSymbol "mori.Map.prototype.entries" (.. cljs.core/PersistentHashMap -prototype -entries))
(goog/exportSymbol "mori.Map.prototype.values" (.. cljs.core/PersistentHashMap -prototype -values))
(goog/exportSymbol "mori.Map.prototype.forEach" (.. cljs.core/PersistentHashMap -prototype -forEach))

(def ^:export set cljs.core/set)
(def ^:export Set cljs.core/PersistentHashSet)
(property-export "Set.prototype.get" cljs.core/get)
(property-export "Set.prototype.conj" cljs.core/conj)
(property-export "Set.prototype.empty" cljs.core/empty)
(property-export "Set.prototype.count" cljs.core/count)
(property-export "Set.prototype.key" cljs.core/key)
(property-export "Set.prototype.val" cljs.core/val)
(property-export "Set.prototype.contains" cljs.core/contains?)
(property-export "Set.prototype.disjoin" cljs.core/disj)


(goog/exportSymbol "mori.Set.prototype.has" (.. cljs.core/PersistentHashSet -prototype -has))
(goog/exportSymbol "mori.Set.prototype.equiv" (.. cljs.core/PersistentHashSet -prototype -equiv))
(goog/exportSymbol "mori.Set.prototype.keys" (.. cljs.core/PersistentHashSet -prototype -keys))
(goog/exportSymbol "mori.Set.prototype.entries" (.. cljs.core/PersistentHashSet -prototype -entries))
(goog/exportSymbol "mori.Set.prototype.values" (.. cljs.core/PersistentHashSet -prototype -values))
(goog/exportSymbol "mori.Set.prototype.forEach" (.. cljs.core/PersistentHashSet -prototype -forEach))
(defn ^:export configure [variable value]
  (case variable
    "print-length" (set! *print-length* value)
    "print-level" (set! *print-level* value)))

(def ^:export meta cljs.core/meta)
(def ^:export withMeta cljs.core/with-meta)
(def ^:export varyMeta cljs.core/vary-meta)
(def ^:export alterMeta cljs.core/alter-meta!)
(def ^:export resetMeta cljs.core/reset-meta!)

(make-inspectable
  cljs.core.LazySeq
  cljs.core.IndexedSeq
  cljs.core.RSeq
  cljs.core.PersistentTreeMapSeq
  cljs.core.NodeSeq
  cljs.core.ArrayNodeSeq
  cljs.core.List
  cljs.core.Cons
  cljs.core.EmptyList
  cljs.core.PersistentVector
  cljs.core.ChunkedCons
  cljs.core.ChunkedSeq
  cljs.core.Subvec
  cljs.core.BlackNode
  cljs.core.RedNode
  cljs.core.ObjMap
  cljs.core.PersistentArrayMap
  cljs.core.PersistentHashMap
  cljs.core.PersistentTreeMap
  cljs.core.PersistentHashSet
  cljs.core.PersistentTreeSet
  cljs.core.Range
  cljs.core.Keyword
  cljs.core.Symbol
  cljs.core.PersistentQueue
  cljs.core.PersistentQueueSeq)
