(ns edn-formatter.css
  (:require-macros
   [garden.def :refer [defstyles]])
  (:require
   [garden.core :as garden]
   [garden.units :refer [px]]))

(defstyles styles
  [:body
   {:-webkit-font-smoothing "antialiased"
    :font {:size (px 13)
           :family "monospace"}
    :line-height (px 18)
    :background {:color :#202026}
    :color :#bfbfbf}]

  [:.number {:color :#ee5f64}]

  [:.keyword {:color :#89bdff}]

  [:.string {:color :#43bfbf}]

  [:.toggle-button
   {:color :#fff
    :font-weight "bold"}])

(defn style-el []
  (let [el (.createElement js/document "style")]
    (.insertAdjacentHTML el "beforeend" (garden/css styles))
    el))
