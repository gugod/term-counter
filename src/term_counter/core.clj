(ns term-counter.core
  (:require [clojure.data.json :as json])
  (:use ring.middleware.params
        ring.util.response))

(def mem (atom {}))

(defn add_count_term [term] 
  (swap! mem assoc term (inc (or (@mem term) 0) ))
  (find @mem term))

(defn addcount [q]
  (let [terms (clojure.string/split q #"\s+")]
    (response (json/write-str (mapv add_count_term terms)))))

(defn handler [request]
  (case (:request-method request)
    :post
    (addcount ((request :query-params) "q"))
    :get
    (response (json/write-str @mem))))

(def app (wrap-params handler))
