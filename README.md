# conjs, a fork of mori

[![Join the chat at https://gitter.im/jcouyang/conjs](https://badges.gitter.im/jcouyang/conjs.svg)](https://gitter.im/jcouyang/conjs?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

[![Build Status](https://travis-ci.org/jcouyang/conjs.svg?branch=master)](https://travis-ci.org/jcouyang/conjs)

<img src="http://cloud.github.com/downloads/swannodette/mori/mori.png" alt="Mori" title="Mori"/>

A simple bridge to ClojureScript's persistent data structures and
[supporting APIs](http://swannodette.github.io/mori/) for vanilla
JavaScript. Pull requests welcome.

## Breaking Changes in Conjs 0.4.1

### core.async

channel ported from core.async, and aync put, get and alts, natively callback, not generator and statemachines.

```js
var c1 = async.chan()
var c2 = async.chan()

async.doAlts(function(v) {
  expect(mori.get(v, 0)).toBe('c1')
  expect(mori.equals(c1, v.a(1))).toBe(true)
  done()
},[c1,c2])
async.put$(c1, 'c1')
async.put$(c2, 'c2')
```

and **Promise** version of channel

```js
async.alts([c1,c2])
  .then(function(v) {
    expect(v.get(0)).toBe('c1')
    expect(mori.equals(c1, v.get(1))).toBe(true)
  })
  .then(done)
async.put(c1, 'c1').then(function(){console.log('put c1 into c1')})
async.put(c2, 'c2').then(function(){console.log('put c2 into c2')})
```

#### goroutine with generator

just `yield` before take or put promise, you'll get a very nice CSP style goroutine

```js
var c = async.chan();
async.go(function*(){
  var expected = yield "something in channel"
  var val = yield c.take();
  expect(val).toBe(expected);
  done()
})
async.put(c, 'something in channel');
```

#### async/await
so if you're using babel with es7 `async function` feature, then you'll also get a very nice CSP style of taking, puting and atlsing a Channel.

```js
var a = mori.async;
(async function(){
  var v = await a.atls([c1,c2]);
  expect(v.get(0)).toBe('c1')
  expect(mori.equals(c1, v.get(1))).toBe(true)
})()

(async function(){
  await a.put(c1, 'c1')
  console.log('put c1 into c1')
})()
(async function(){
  await a.put(c2, 'c2')
  console.log('put c2 into c2')
})()  
```

### Idiomatic JS method names

instead of
```js
var a = mori.hashMap(1,2,3,4)
mori.get(a, 1)
mori.assoc(a, 1, 6)
```

there also JS method of your choice

```js
var a = mori.hashMap(1,2,3,4)
a.get(1) // => 2
a.assoc(1,6) // => {1 6, 3 4}
```

## Breaking changes in 0.3.0 

The API now uses idiomatic JavaScript naming conventions.

## Improvements to 0.3.0

### Faster

Mori is considerably faster across the board thanks to recent
enhancements to the ClojureScript compiler. For users who would like
to benchmark their immutable data structure implementations against
Mori, Mori now exposes direct arity invokes which eliminates previous
calling overheads from arity dispatching. See
[Benchmarking](https://github.com/swannodette/mori/wiki/Benchmarking)
for more information.

Mori hash maps now default to ClojureScript `ArrayMap`s that are
automatically promoted to `PersistentHashMap`s as needed. `ArrayMap`s
deliver considerably better performance at small sizes and when simple
keys are at play. For example a Mori hash map with less than or equal
to eight keys can now be built nearly an order of magnitude faster than
Immutable.js 3.6.2 `Map`s.

### More ES6

All Mori collections support ES6 iteration via `foo[Symbol.iterator]`
or `foo["@@iterator"]`.

## Differences from Immutable.js

* A functional API, data structures do not have public methods
* Faster, ClojureScript data structures have been subjected to more
  real world usage and continuous benchmarking for nearly 4 years
* Larger, gzipped the base Mori module is about 6K larger than Immutable.js

## Getting it

You can install the latest release via npm:

```shell
npm install https://github.com/bjoyx/mori.git
```

The installed package contains a single optimized JavaScript file `mori.js`.

Load `mori` in your Node.js programs as you would any other module:

```javascript
var mori = require("mori");
```

In a browser, you can load mori with a script tag, as you would any other JavaScript library:

```html
<script src="mori.js" type="text/javascript"></script>
```

You can also load it as an AMD module, e.g. with [RequireJS](http://requirejs.org/).

## Usage

You can use it from your projects like so:

```javascript
var inc = function(n) {
  return n+1;
};

mori.toArray(mori.map(inc, mori.vector(1,2,3,4,5)));
// => [2,3,4,5,6]
```

Efficient non-destructive updates!

```javascript
var v1 = mori.vector(1,2,3);
var v2 = mori.conj(v1, 4);
v1.toString(); // => '[1 2 3]'
v2.toString(); // => '[1 2 3 4]'
```

```javascript
var sum = function(a, b) {
  return a + b;
};
mori.reduce(sum, mori.vector(1, 2, 3, 4)); // => 10
```

Lazy sequences!

```javascript
var _ = mori;
_.toArray(_.interpose("foo", _.vector(1, 2, 3, 4)));
// => [1, "foo", 2, "foo", 3, "foo", 4]
```

Or if it's more your speed, use it from CoffeeScript!

```coffeescript
inc = (x) -> x+1  
r = mori.map inc, mori.vector(1,2,3,4,5)
mori.toArray r
```

### Documentation

You can find extensive [documentation and examples](http://swannodette.github.io/mori/) here.

## More Examples

### Efficient Freeze/Thaw

For vectors and maps we provide an efficient thaw and freeze
operations:

```javascript
var m = mori;

// ~220ms with V8 version 3.29.80 MBP 2.26ghz
for(var j = 0; j < 10; j++) {
  var s = new Date();
  var arr = [];
  for(var i = 0; i < 10000000; i++) {
    arr.push(i);
  }
  print("Array push " + arr.length + " items " + ((new Date())-s));
  gc();
}

// ~70ms
for(var j = 0; j < 10; j++) {
  s = new Date();
  var mv = m._thaw(m.vector());
  for(var i = 0; i < 10000000; i++) {
    mv = m._conj.f2(mv, i);
  }
  var v = m._freeze(mv);
  print("Mutable vector conj " + m.count(v) + " items " + ((new Date())-s));
  gc();
}
```

### ES6 Map/Set inspired interfaces

All Mori maps and sets support all the non-mutating methods of the
proposed ES6
[Map](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Map)
and
[Set](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Set)
interfaces. The main difference with the spec is that key lookup is
based on value not reference. `keys`, `values`, and `entries` methods
return the proposed mutable iterators:

```javascript
var m = mori;
var h = m.hashMap("foo", 1, "bar", 2);

h.has("foo"); // => true
h.get("foo"); // => 1

var iter = h.keys();
iter.next(); // => {done: false, value: "foo"}
```

This feature is subject to changes in the ES6 proposal.

### Transducers

Mori includes Transducers. Zero allocation collection operations FTW:

```javascript
var m = mori;
var a = [];

for(var i = 0; i < 1000000; i++) {
  a.push(i);
}

// make it immutable
var v = m.into(m.vector(), a);

function time(f) {
  var s = new Date();
  f();
  console.log(((new Date())-s)+"ms");
}

// ~190ms V8 version 3.29.80 MBP 2.26ghz
time(function() {
  var xf = m.comp(m.map(m.inc), m.map(m.inc), m.map(m.inc));
  return m.transduce(xf, m.completing(m.sum), 0, v);
}, 10);

// ~440ms
time(function() {
  return a.map(m.inc).map(m.inc).map(m.inc).reduce(function(a,b){return a+b;}, 0);
}, 10);
```

## Build

### Prerequisites

You will first need to install the
[Java](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
SDK, if it's not already installed on your system.

On Windows, you will need to manually install
[Leiningen](http://github.com/technomancy/leiningen). On UNIX-like
systems, Leiningen will be installed within the project automatically
if the `lein` executable is not found on your path or if your `lein`
version predates `2.0.0`.

### Clone the repo

```shell
git clone https://github.com/swannodette/mori.git
cd mori
```

### On a UNIX-like system build with

```shell
./scripts/build.sh
```

### Alternatively using npm

```shell
npm run-script build
```

### On Windows

```shell
./scripts/build.ps1
```

The build process will generate an optimized JavaScript file
`mori.js`, which is suitable for use with Node.js, or in a Web browser
or other JavaScript environments. You can also load it as an AMD
module.

Copyright (C) 2012-2015 David Nolen and contributors

Distributed under the
[Eclipse Public License](https://raw.github.com/swannodette/mori/master/epl-v10.html),
the same as Clojure.
