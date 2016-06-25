(ns com.nomistech.core-async-extras
  (:require [clojure.core.async :as a]))

(defn stoppable-go-loop
  "set up a go loop that calls `f` at intervals of `frequency-ms`.
  Return a control channel.
  When `:stop` is put on the control channel, exit the go loop."
  [f frequency-ms]
  (let [control-ch (a/chan)]
    (a/go-loop []
      (f)
      (let [[v ch] (a/alts! [control-ch
                             (a/timeout frequency-ms)]
                            :priority true)]
        (when-not (= v :stop)
          (recur))))
    control-ch))
