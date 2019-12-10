(ns fuel-counter-upper.core-test
  (:require [clojure.test :refer :all]
            [fuel-counter-upper.core :refer :all :as sut]))

(deftest fuel
  (testing "Check total-fuel for a number of test module weights"
    (is (= (sut/total-fuel 100756) 50346))
    (is (= (sut/total-fuel 1969) 966))
    (is (= (sut/total-fuel 14) 2))))
