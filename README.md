# ThrowAway

## Motivation

*A desire to better facilitate functional core, imperative shell programming[^1].*

### Functional Core

I often find myself mixing *throwaway* side effects with what should be pure functions (functional core). This includes dispatching logs, metrics, traces, and updates (e.g. "system X about to deploy Y", "Y has been deployed to system X", etc).

For example:

```clojure
(require '[taoensso.timbre :refer [debug error]])

;; this function is not pure because I am logging to STDOUT
(defn do-something-core-like
  [x]
  (let [_ (debug x) ;; log debug pre-transformation
        y (inc x) ;; let's pretend this is some important transformation
        _ (debug y) ;; log debug post-transformation
        _ (when (< y 10) (error y))] ;; when condition is met, log error post-transformation
    y))
```

### Imperative Shell

I also like using threading macros[^2] (imperative shell), but find it cumbersome to incorporate *throwaway* side effects into my pipeline without having to ensure they return a value that can be used by a following form. Not to mention the library wrappers I constantly find myself writing just to remain in the pipeline.

For example:

```clojure
(require '[taoensso.timbre :refer [info]])

(defn wrap-info
  ([log] (wrap-info log ""))
  ([log arg]
    (info (format log arg))
    arg))
```

### Throwaway Side Effects

**Definition:** A function, in its current context, you do not care what its expected return value is.

## Implementation

Long story short, it's a mix of `constantly`[^3] and `run!`[^4], wrapped in a `try`[^5]/`catch`[^6] exception handler.

## Syntax

```clojure
(require '[throwaway :refer [constantly-run-first!]]
         '[taoensso.timbre :refer [info]])

;; when threading as 1st argument
(-> 1 ;; is 1
    inc ;; is now 2
    (constantly-run-first! info) ;; logs "2" once, returns 2
    (* 3) ;; is now 6
    (constantly-run-first! info info)) ;; logs "6" twice, returns 6
    (/ 2) ;; is now 3
    (constantly-run-first! #(info (format "final value: %s" %)))) ;; logs "final value: 3", returns 3
3
```

[^1]: https://kumarshantanu.medium.com/organizing-clojure-code-with-functional-core-imperative-shell-2f2ee869faa2

[^2]: https://clojure.org/guides/threading_macros

[^3]: https://clojuredocs.org/clojure.core/constantly

[^4]: https://clojuredocs.org/clojure.core/run!

[^5]: https://clojuredocs.org/clojure.core/try

[^6]: https://clojuredocs.org/clojure.core/catch
