(ns edn-formatter.background
  (:require
   [edn-formatter.util :refer [background?]]))

(def +edn-mime-type-regexp+ #"application/edn")

(defn is-header? [header k]
  (= (.toLowerCase (name k))
     (.toLowerCase (:name header))))

(defn update-header [headers k f]
  (for [header headers]
    (if (is-header? header k)
      (update-in header [:value] f)
      header)))

(defn on-headers-recieved [cb filter-map & [opts]]
  (js/chrome.webRequest.onHeadersReceived.addListener
   (fn [details]
     (-> details
         (js->clj :keywordize-keys true)
         cb
         clj->js))
   (clj->js filter-map)
   (when opts (clj->js opts))))

(when background?
  (on-headers-recieved
   (fn [details]
     (-> details
         (select-keys [:responseHeaders])
         (update-in
          [:responseHeaders] update-header :content-type
          (fn [content-type]
            (if (re-find +edn-mime-type-regexp+ content-type)
              "text/edn"
              content-type)))))
   {:urls ["<all_urls>"]
    :types ["main_frame" "sub_frame"]}
   ["blocking" "responseHeaders"]))
