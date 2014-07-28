(ns term-counter.core
  (:use ring.middleware.params
        ring.util.response))

(def mem (atom {}))

(defn add_count_term [term] 
  (swap! mem assoc term (inc (or (@mem term) 0) ))
  (find @mem term))

(defn addcount [q]
  (let [terms (clojure.string/split q #"\s+")]
    (println (str (mapv add_count_term terms)))
    (response "OK")))

(defn handler [request]
  (case (:request-method request)
    :post
    (addcount ((request :query-params) "q"))
    :get
    (response (str @mem))))

(def app (wrap-params handler))
