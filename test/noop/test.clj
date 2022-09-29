(ns noop.test
  (:require [clojure.test :refer [deftest is testing]]
            [noop :refer [constantly-run-first!]]))

(defn f
  [x]
  (println (str "some side effect using: " x)))

(deftest constantly-first-run-test
  (let [x 1]
    (testing "i always return nil when passing 0 args"
      (is (= (constantly-run-first!) nil)))

    (testing "i always return the 1st value when passing 1 arg"
      (is (= (constantly-run-first! x) x)))

    (testing "i always return the 1st value when passing 2 args"
      (is (= (constantly-run-first! x f) x)))

    (testing "i always return the 1st value when passing many args"
      (is (= (constantly-run-first! x f f f f) x)))))