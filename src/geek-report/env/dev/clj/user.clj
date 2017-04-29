(ns user
  (:require [mount.core :as mount]
            geek-report.core))

(defn start []
  (mount/start-without #'geek-report.core/repl-server))

(defn stop []
  (mount/stop-except #'geek-report.core/repl-server))

(defn restart []
  (stop)
  (start))


