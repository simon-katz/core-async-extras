(ns nomis-async-test
  (:require [clj-time.core :as t]
            [clojure.core.async :as a]
            [clj-time.coerce :as c]
            [nomis-async :refer :all]
            [midje.sweet :refer :all]))

(fact "`stoppable-go-loop` should work"
  ;; TODO: Is this kind of test with timing ok? How else to do it?
  ;; Given
  (let [frequency-ms    100
        time-to-run-for 350
        acc-atom        (atom [])
        start-time      (t/now)]
    ;; When
    (let [control-ch (stoppable-go-loop #(swap! acc-atom conj (t/now))
                                        frequency-ms)]
      (Thread/sleep time-to-run-for)
      (a/>!! control-ch :stop)
      ;; Then
      (let [ts @acc-atom
            diffs (map (comp t/in-millis t/interval)
                       ts
                       (rest ts))]
        (fact "should first call the function immediately"
          (c/to-long (first ts))
          => (roughly (c/to-long start-time)
                      10))
        (fact "should call the function at intervals of `frequency-ms`, until `time-to-run-for` has elapsed"
          diffs
          => (let [expected-n-iterations (int (/ time-to-run-for
                                                 frequency-ms))]
               (just (repeat expected-n-iterations
                             (roughly frequency-ms 10)))))))))
