(ns centipair.core.utilities.offline
  (:require [noir.response :as response]))


(defn news-cache []
  (response/content-type 
   "text/cache-manifest; charset=utf-8" 
   (response/status 
    200
    "CACHE MANIFEST
# 2010-06-18:v2

# Explicitly cached 'master entries'.
CACHE:
#/favicon.ico
/pure/css/pure-min.css
/pure/css/layouts/side-menu-old-ie.css
/pure/css/layouts/side-menu.css
/pure/css/grids-responsive-old-ie-min.css
/pure/css/grids-responsive-min.css
/pure/js/ui.js
/js/react.js
/js/main.js

# Resources that require the user to be online.
NETWORK:
*

# static.html will be served if main.py is inaccessible
# offline.jpg will be served in place of all images in images/large/
# offline.html will be served in place of all other .html files
#FALLBACK:
#/main.py /static.html
#images/large/ images/offline.jpg")))
