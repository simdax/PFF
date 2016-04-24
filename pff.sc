PFF : Pattern{
	var dur, pattern;

	*new  { |dur, pattern|
		^super.newCopyArgs(dur, pattern)
	}
	embedInStream  { |inval|
		var stream = pattern.asStream,
		durToDrop = dur.value(inval),
		now = 0, event;
		while {
			now < durToDrop
		}
		{
			event = stream.next(inval);
			if((now+event.delta) > durToDrop)
			{
				var offset = now+event.delta-durToDrop;
				inval=(event++(delta:offset, dur:offset)).yield;
			};
			now = now + event.delta
		};
		stream.embedInStream(inval);
		^inval
	}
}


/*

a=Pbind(\degree, Pseries(), \dur, Pseq([1,2,3],inf));
PFF(4, a, 1 ).midi.trace.play


a=	Ptpar([
	3, Pmel(4),
]).midi;
PFF(2.9, a).trace.play

Quarks.install("https://github.com/simdax/PFF.git")

*/

