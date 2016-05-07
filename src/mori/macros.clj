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

(defmacro mori-export [exportf coref]
  (let [{:keys [ns name arglists]} (ana-api/resolve &env coref)
        arglists (cond-> arglists
                   (= (first arglists) 'quote) rest)]
    (letfn [(export-method [arglist]
              (let [c (count arglist)]
                `(js/goog.exportSymbol
                   ~(str "mori." (core/name exportf) ".f" c)
                   ~(symbol (str ns)
                      (str (core/name name) ".cljs$core$IFn$_invoke$arity$" c)))))]
      `(do
         (js/goog.exportSymbol ~(str "mori." (core/name exportf)) ~coref) ~(list 'js* ";")
         ~@(when (and arglists (< 1 (count arglists)))
             (map export-method (remove #(some '#{&} %) arglists)))))))
