(ns geek-report.routes.home
  (:require [geek-report.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]))

(defn home-page []
  (layout/render "index.html"))

(defroutes home-routes
           (GET "/" [] (home-page)))

