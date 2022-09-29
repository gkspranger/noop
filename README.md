# noop

## Motivation

*A desire to better facilitate functional core, imperative shell programming[^1].*

### Functional Core

I often find myself mixing side effects with what should be pure functions (functional core). This includes pushing logs, metrics, traces, and updates (e.g. "system X about to deploy Y", "Y has been deployed to system X", etc).

For example:

```clojure
;; this function is not pure because I am writing to STDOUT
(defn do-something-core-like
  [x]
  (let [_ (log/debug x) ;; logging debug pre-transformation
        y (inc x) ;; let's pretend this is some important transformation
        _ (log/debug y) ;; log debug post-transformation
        _ (when (< y 10) log/error y) ;; log error post-transformation, when condition met
       ]
    y))
```

### Imperative Shell

I also like using threading macros[^2], but I find it difficult to incorporate side effects into my pipeline without having to ensure side effects return an argument.

I wanted to find an easier way to allow myself to continue to use

[^1]: https://kumarshantanu.medium.com/organizing-clojure-code-with-functional-core-imperative-shell-2f2ee869faa2

[^2]: https://clojure.org/guides/threading_macros

## Syntax

```clojure
(require '(noop))

;; when threading as 1st argument
(-> 1 ;; is 1
    inc ;; is now 2
    (noop/constantly-run-first! println) ;; prints "2" once, returns 2
    (* 3) ;; is now 6
    (noop/constantly-run-first! println println)) ;; prints "6" twice, returns 6
```
