(defproject mori "0.5.0-SNAPSHOT"
  :description "Persistent Data Structures for JavaScript"

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.170"]
                 [org.clojure/core.async "0.2.374"]]

  :plugins [[lein-cljsbuild "1.1.1"]]

  :clean-targets ["dev" "release" "target"]
  
  :cljsbuild
  {:builds
    [;; mori
     {:source-paths ["src"],
      :id "dev",
      :compiler
      {:output-to      "dev/mori.dev.js",
       :output-dir     "dev/"
       :optimizations  :simple
       :cache-analysis true
       :output-wrapper false
       :pretty-print   true}}

     {:source-paths ["src"],
      :id "release"
      :compiler
      {:optimizations  :advanced
       :output-dir     "release"
       :output-wrapper false
       :pretty-print   false
       :verbose        true
       :modules
       {:cljs-base {:entries #{cljs.core}
                    :output-to "release/build/cljs.core.js"}
        :mori {:entries #{mori}
                    :output-to "release/build/mori.js"}
        :mutable   {:entries #{mori.mutable}
                    :output-to "release/build/mori.mutable.js"}
        :extra     {:entries #{clojure.data cljs.reader clojure.set mori.extra}
                    :output-to "release/build/mori.extra.js"}
        :core.async     {:entries #{mori.async}
                    :output-to "release/build/mori.async.js"}}}}]})
