Function::getter = (prop, get) ->
  Object.defineProperty @prototype, prop, {get, configurable: yes}

Function::setter = (prop, set) ->
  Object.defineProperty @prototype, prop, {set, configurable: yes}  

module.exports = 
	Signal: class Signal
		constructor: (@expr) ->
			@observers = new Set([])
			@value = @eval()

		@callers: []

		getValue: -> 
			callers = Signal.callers
			if (callers.length > 0) 
				lastCaller = callers[callers.length - 1]
				@observers.add(lastCaller)
				if (lastCaller.observers.has(this)) 
					throw "Circular reference detected"

			return @value

		update: ->
			throw "Unsupported operation"

		@getter 'val', -> @getValue()
		@setter 'val', (expr) -> @update(expr)		

		eval: -> 
			Signal.callers.push(@)

			newValue = @expr()
			if (newValue != @value) 
				@value = newValue
				oldObservers = new Set(@observers)
				@observers.clear()
				oldObservers.forEach (observer) -> observer.eval()
			Signal.callers.pop()

			return @value

	Var: class Var extends Signal
		constructor: (expr) -> 
			super(expr)

		update: (expr) -> 
			@expr = expr
			@eval()		