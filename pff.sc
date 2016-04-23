PFF : Pattern{
	var dur, pattern, tolerance;
	//	var canEmbed = true;
	//asStream  { ^Routine({ |inval| this.embedInStream(inval) }) };
	*new  { |dur, pattern, tolerance = 0.001|
		^super.newCopyArgs(dur, pattern, tolerance)
	}
	embedInStream  { |inval|
		var stream = pattern.asStream,
		durToDrop = dur.value(inval),
		now = 0, event;
		while { (now absdif: durToDrop) > tolerance and: {
			(event = stream.next(inval.copy)).notNil
		} } {
			now = now + event.delta;
		};
		stream.embedInStream(inval)
	}
}


/*

a=Pbind(\degree, Pseries()).midi(5);
PFF( 15, a ).play

*/