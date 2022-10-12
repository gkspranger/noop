(ns throwaway)

(defn- run-impl
  [keep args]
  (run! #(try (% keep) (catch Exception _)) args)
  keep)

(defn constantly-run-first!
  "Runs the supplied procedures (any argument that is not the first argument)
   for purposes of side effects, while always passing the first argument as a
   parameter to successive arguments. Returns the first argument."
  [& args]
  (run-impl (first args) (rest args)))

(defn constantly-run-last!
  "Runs the supplied procedures (any argument that is not the last argument)
   for purposes of side effects, while always passing the last argument as a
   parameter to preceding arguments. Returns the last argument."
  [& args]
  (run-impl (last args) (drop-last args)))
