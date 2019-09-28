# nutrack

[![CircleCI](https://circleci.com/gh/mikepjb/nutrack.svg?style=svg)](https://circleci.com/gh/mikepjb/nutrack)

Understand the value in your food.

This is a web application that breaksdown the value of your weekly shop, in
terms of money spent and what you get for it.

Ever wondered what your most cost effective protein source is?

Wonder no more. [nutrack.michaelbruce.co]

### Getting Started

To develop this product, I am using Emacs + Cider:

Use the `cider-jack-in-clj&cljs` command to open a Clojure REPL, Clojurescript
REPL and browser page that will live reload your changes.

The reason for starting these 3 things is that this repository contains both the
backend server code and the frontend client code. The Clojurescript REPL/Browser
is provided to hot reload client side code on the fly for quick feedback and the
Clojure REPL evaluates server-side code.

Once the jack-in is complete (you should have two REPLs and a newly open browser
tab), evaluate nutrack.core/start-server to start the backend.

To work on the command line:

`clojure -m figwheel.main --build nutrack`
