(ns com.nomistech.core-async-extras-test
  (:require [clj-time.core :as t]
            [clojure.core.async :as a]
            [com.nomistech.core-async-extras :refer :all]
            [midje.sweet :refer :all]))

(fact "`stoppable-go-loop` works"
  (let [frequency-ms    100
        time-to-run-for 350
        acc-atom        (atom [])
        control-ch      (stoppable-go-loop #(swap! acc-atom conj (t/now))
                                           frequency-ms)]
    (Thread/sleep time-to-run-for)
    (a/>!! control-ch :stop)
    (let [ts @acc-atom
          diffs (map (comp t/in-millis t/interval) ts (rest ts))]
      (fact
        diffs
        => (let [expected-n-iterations (int (/ time-to-run-for
                                               frequency-ms))]
             (just (repeat expected-n-iterations
                           (roughly frequency-ms 10))))))))
