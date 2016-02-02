#!/bin/sh

set -e

./bin/lein_prefer deps
./bin/lein_prefer cljsbuild once release

echo "Finalizing mori.js"

(cat support/wrapper.beg.txt;
 cat ./release/build/base.js;
 cat ./release/build/mori.js;
 cat support/wrapper.end.txt) > mori.js

(cat support/wrapper.beg.txt;
 cat ./release/build/base.js;
 cat ./release/build/mori.js;
 cat ./release/build/mori.async.js;
 cat support/wrapper.end.txt) > withAsync.js

echo "Build finished."
