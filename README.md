# nomis-async

Useful things on top of core.async.

**This documentation tracks the `master` branch. Consult
the relevant Git tag (e.g. `0.1.2`) if you need documentation for a
specific release.**


## Installation

Current version:

[![Clojars Project](https://img.shields.io/clojars/v/nomis-async.svg)](https://clojars.org/nomis-async)

Consider excluding the dependencies on Clojure itself and on core.async by
using a dependency vector like this`:

```
  [nomis-async "x.x.x" :exclusions [org.clojure/clojure
                                    org.clojure/core.async]]
```


## Usage

### repeat-at-intervals

This function sets up a go loop that calls a supplied function at specified
intervals.
It returns a control channel.
When `:stop` is put on the control channel, exits the go loop.
  

```
(require '[clojure.core.async :as a])
(require '[nomis-async :as na])

(def control-ch (na/repeat-at-intervals #(println "Hello")
                                        1000))
;; Hello
;; Hello
;; Hello
;; Hello
;; Hello

(a/>!! control-ch :stop)
```



## License

Copyright © 2016-2018 Simon Katz

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
