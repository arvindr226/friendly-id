package com.devskiller.friendly_id;

import java.math.BigInteger;
import java.util.UUID;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.test.Arbitrary;
import io.vavr.test.Gen;
import org.assertj.core.util.Strings;

class DataProvider {

	static Arbitrary<UUID> UUIDS = ignored -> {
		Gen<Long> longs = Gen.choose(Long.MIN_VALUE, Long.MAX_VALUE);
		return random -> new UUID(longs.apply(random), longs.apply(random));
	};

	static Arbitrary<BigInteger> POSITIVE_BIG_INTEGERS = ignored -> {
		Gen<Long> longs = Gen.choose(Long.MIN_VALUE, Long.MAX_VALUE)
				.filter(value -> value >= 0);
		return random -> BigInteger.valueOf(longs.apply(random)).multiply(BigInteger.valueOf(longs.apply(random)));
	};

	static Arbitrary<String> FRIENDLY_IDS = Arbitrary.string(
			Gen.frequency(
					Tuple.of(1, Gen.choose('A', 'Z')),
					Tuple.of(1, Gen.choose('a', 'z')),
					Tuple.of(1, Gen.choose('0', '9'))))
			.filter(code -> !Strings.isNullOrEmpty(code))
			.filter(code -> Base62.decode(code, -1).bitLength() <= 128);

	static Arbitrary<Tuple2<Long, Long>> LONG_PAIRS = ignored -> {
		Gen<Long> longs = Gen.choose(Long.MIN_VALUE, Long.MAX_VALUE);
		return random -> Tuple.of(longs.apply(random), longs.apply(random));
	};

}

