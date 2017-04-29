(ns geek-report.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[geek-report started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[geek-report has shut down successfully]=-"))
   :middleware identity})
