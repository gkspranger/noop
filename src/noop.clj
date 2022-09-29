(ns noop)

(defn constantly-run-first!
  [& args]
  (let [keep (first args)]
    (run! #(% keep) (rest args))
    keep))
