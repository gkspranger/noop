(ns throwaway.test
  (:require [clojure.test :refer [deftest is testing]]
            [throwaway :refer [constantly-run-first!
                               constantly-run-last!]]))

(defn f
  [x]
  (println (str "some side effect using: " x)))

(defn t
  [x]
  (throw (Exception. (println
                      (str "some side effect that throws an exception using: "
                           x)))))

(deftest constantly-run-first-test
  (let [x "first"]
    (testing "i always return nil when passing 0 args"
      (is (= (constantly-run-first!) nil)))

    (testing "i always return the first value when passing 1 arg"
      (is (= (constantly-run-first! x) x)))

    (testing "i always return the first value when passing 2 args"
      (is (= (constantly-run-first! x f) x)))

    (testing "i always return the first value when passing many args"
      (is (= (constantly-run-first! x f f f f) x)))

    (testing "i always return the first value when passing args that
              throw an exception"
      (is (= (constantly-run-first! x f t f t) x)))))

(deftest constantly-run-last-test
  (let [x "last"]
    (testing "i always return nil when passing 0 args"
      (is (= (constantly-run-last!) nil)))

    (testing "i always return the last value when passing 1 arg"
      (is (= (constantly-run-last! x) x)))

    (testing "i always return the last value when passing 2 args"
      (is (= (constantly-run-last! f x) x)))

    (testing "i always return the last value when passing many args"
      (is (= (constantly-run-last! f f f f x) x)))

    (testing "i always return the last value when passing args that
              throw an exception"
      (is (= (constantly-run-last! f t f t x) x)))))
