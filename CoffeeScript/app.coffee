signals = require("#{__dirname}/signals")

a = new signals.Var(-> 1)
b = new signals.Var(-> 2)
c = new signals.Signal(-> a.val**2 + b.val**2)
console.log c.val

a.val = -> 3
b.val = -> 4
console.log c.val
