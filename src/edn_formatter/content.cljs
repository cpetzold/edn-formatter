(ns edn-formatter.content
  (:require
   [cljs.reader :as reader]
   [om.core :as om :include-macros true]
   [ankha.core :as ankha]
   [edn-formatter.util :refer [background?]]
   [edn-formatter.css :as css]))

(reader/register-default-tag-parser!
  (fn [tag value]
    (str tag "# " value)))

(defn div [id]
  (let [el (js/document.createElement "div")]
    (set! (.-id el) id)
    el))

(defn send-message [message cb]
  (.sendMessage js/chrome.runtime (clj->js message)
                #(cb (js->clj % :keywordize-keys true))))

(defn edn-request? [cb]
  (send-message "edn?" cb))

(defn init-format [e]
  (edn-request?
   (fn [edn?]
     (when edn?
       (let [pre js/document.body.firstChild
             edn-str (.-textContent pre)
             edn (reader/read-string edn-str)
             container (div "container")]
         (.removeChild js/document.body pre)
         (.appendChild js/document.head (css/style-el))
         (.appendChild js/document.body container)

         (js/console.log "%cEDN Formatter" "font-weight: bold")
         (js/console.log (clj->js edn))

         (om/root ankha/inspector edn {:target container}))))))

(when-not background?
  (.addEventListener
   js/document "DOMContentLoaded" init-format false))
