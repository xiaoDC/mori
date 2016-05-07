(defproject mori "0.5.0-SNAPSHOT"
  :description "Persistent Data Structures for JavaScript"

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.8.34"]
                 [org.clojure/core.async "0.2.374"]]

  :plugins [[lein-cljsbuild "1.1.2"]]

  :clean-targets ["dev" "release" "target"]

  :cljsbuild
  {:builds
   [;; mori
         {:source-paths ["src"],
      :id "dev"
      :compiler
      {:optimizations  :simple
       :output-dir     "dev"
       :output-wrapper false
       :pretty-print   true
       :verbose        true
       :language-out :ecmascript5
       :modules
       {:mori {:entries #{mori mori.async}
               :output-to "dev/build/mori.js"}
        }}}
     {:source-paths ["src"],
      :id "release"
      :compiler
      {:optimizations  :advanced
       :output-dir     "release"
       :output-wrapper false
       :verbose        true
       :modules
       {:cljs-base {:entries #{cljs.core}
                    :output-to "release/build/base.js"}
        :core.async {:entries #{cljs.core.async.impl.protocols cljs.core.async.impl.ioc-helpers cljs.core.async.impl.buffers cljs.core.async.impl.dispatch cljs.core.async.impl.channels cljs.core.async.impl.timers goog.async.FreeList goog.async.WorkItem goog.Thenable goog.async.run goog.promise.Resolver goog.Promise cljs.core.async goog.async.throwException}
                     :output-to "release/build/core.async.js"}
        :debug {:entries #{goog.dom.TagName goog.dom.NodeType goog.debug.Error goog.asserts goog.debug.EntryPointMonitor }
                :output-to "release/build/mori.debug.js"}
        :async     {:entries #{mori.async}
                    :output-to "release/build/mori.async.js"}

        :mori {:entries #{mori}
               :output-to "release/build/mori.js"}
        }
       }}]})
