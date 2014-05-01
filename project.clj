(defproject edn-formatter "0.1.0-SNAPSHOT"
  :description "Pretty-prints EDN"

  :plugins [[lein-cljsbuild "1.0.1"]]

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2173"]
                 [om "0.6.2"]
                 [ankha "0.1.2"]
                 [garden "1.1.6"]]

  :source-paths ["src"]

  :cljsbuild
  {:builds
   {:client
    {:source-paths ["src"]
     :compiler
     {:output-dir "resources/js/edn-formatter"
      :output-to "resources/js/edn-formatter.js"
      :pretty-print true
      :preamble ["react/react.min.js"]
      :externs ["react/externs/react.js"]}}}}

  :profiles
  {:prod {:cljsbuild
          {:builds
           {:client {:compiler
                     {:optimizations :advanced
                      :pretty-print false}}}}}
   :srcmap {:cljsbuild
            {:builds
             {:client {:compiler
                       {:source-map "resources/js/edn-formatter.js.map"
                        :source-map-path "prism"}}}}}})
