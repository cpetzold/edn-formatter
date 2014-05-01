(ns edn-formatter.content
  (:require
   [cljs.reader :as reader]
   [om.core :as om :include-macros true]
   [ankha.core :as ankha]
   [edn-formatter.util :refer [background?]]))

(defn div [id]
  (let [el (js/document.createElement "div")]
    (set! (.-id el) id)
    el))

(defn init-format [e]
  (let [pre js/document.body.firstChild
        edn-str (.-textContent pre)
        edn (reader/read-string edn-str)
        container (div "container")]
    (.removeChild js/document.body pre)
    (.appendChild js/document.body container)

    (js/console.log "%cEDN Formatter" "font-weight: bold")
    (js/console.log (clj->js edn))

    (om/root ankha/inspector edn {:target container})))

(when-not background?
  (.addEventListener
   js/document "DOMContentLoaded" init-format false))
