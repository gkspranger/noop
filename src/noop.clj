(ns noop
  (:require [clojure.test :refer [function?]]))

(defn do-when-func
  [arg x]
  (when (function? x)
    (x arg)))

(defn noop-first
  [& args]
  (let [keep (first args)
        _ (mapv #(do-when-func keep %) args)]
    keep))

(comment
  
  (noop-first 12 #(println (str "hello: " %)))
  

  




  )