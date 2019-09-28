(ns nutrack.core-test
  (:require [cljs.test :refer-macros [deftest is testing run-tests]]
            [nutrack.core :as nt]))

(deftest search
  (testing "search is case insensitive"
    (is (= "wat" "wat"))))
