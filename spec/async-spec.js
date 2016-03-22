var mori = require('../withAsync');
var async = mori.async;

describe('core.async', function() {
  describe('channel', function() {
    it('take and put from channel',function(done) {
      var c = async.chan()
      async.take$(c ,function(x){
        expect(x).toBe('something in channel')
        done()
      })
      async.put$(c, 'something in channel')
    })

    it('close channel for put', function() {
      var c = async.chan()
      async.close$(c)
      expect(async.put$(c, 'something in channel')).toBe(false)
    })
    it('can create timeout channel', function(done) {
      var now = new Date();
      var tc = async.timeout(200)
      async.take$(tc, function(x) {
        var then = new Date();
        expect(((then-now)/100).toFixed()).toBe("2")
        done()
      })
    })
  })

    describe('promise channel', function() {
        it('take and put from channel',function(done) {
            var c = async.chan()
            async.take(c)
                .then(function(x){
                    expect(x).toBe('something in channel')
                })
                .then(done)
            async.put(c, 'something in channel')
        })
    })

  describe('alts', function() {
    it('race channel', function(done) {
      var c1 = async.chan()
      var c2 = async.chan()
      
      async.doAlts(function(v) {
        expect(mori.get(v, 0)).toBe('c1')
        expect(mori.equals(c1, v.a(1))).toBe(true)
        done()
      },[c1,c2])
      async.put$(c1, 'c1')
      async.put$(c2, 'c2')
    })

      it('race promise channel', function(done) {
          var c1 = async.chan()
          var c2 = async.chan()
          
          async.alts([c1,c2])
              .then(function(v) {
                  expect(mori.get(v, 0)).toBe('c1')
                  expect(mori.equals(c1, v.a(1))).toBe(true)
              
              })
              .then(done)
          async.put$(c1, 'c1')
          async.put$(c2, 'c2')
      })

    it('race channel untile timeout', function(done) {
      var c1 = async.timeout(100)
      var c2 = async.chan()
      var now = new Date()
      async.doAlts(function(v) {
        var then = new Date()
        expect((then-now)>=100).toBe(true)
        done()
      },[c1,c2])
    })
  })
})
