(ns mori.macros
  (:require [cljs.analyzer.api :as ana-api]))

(alias 'core 'clojure.core)

(defn make-inspectable-1 [x]
  `(aset (.-prototype ~x) "inspect"
     (fn []
       (~'this-as coll#
         (.toString coll#)))))

(defmacro make-inspectable [& xs]
  `(do ~@(map make-inspectable-1 xs)))

(defmacro property-export [exportp corep]
  `(js/goog.exportSymbol
    ~(str "mori." exportp)
    (fn [& args#] (apply ~corep (cons (~'js* "this") args#)))))
