var mori = require('../mori');


var inc = function(n) {
  return n+1;
};

var tt = mori.toArray(mori.map(inc, mori.vector(1,2,3,4,5)));

console.log(tt, '==============')
