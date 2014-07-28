# term-counter

Run:

    lein ring server

Send query string with terms...

    curl -XPOST http://localhost:3000/'?q=foo+bar+baz'

Retrieve term counts.

    curl http://localhost:3000/

