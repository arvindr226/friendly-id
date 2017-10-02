package com.devskiller.friendly_id;

import java.util.UUID;

import io.vavr.test.Arbitrary;
import io.vavr.test.Gen;
import org.junit.Test;

import static io.vavr.test.Property.def;

public class Url62Test {

	@Test
	public void resultOfPairingIsPositiveAndCanBeInvertedWithUnpairing() throws Exception {
		Gen<Long> longs = Gen.choose(Long.MIN_VALUE, Long.MAX_VALUE);
		Arbitrary<UUID> uuids = longs
				.flatMap(value -> random -> new UUID(value, longs.apply(random)))
				.arbitrary();
		def("Url62.decode(Url62.encode(uuid)).equals(uuid)")
				.forAll(uuids)
				.suchThat(uuid -> Url62.decode(Url62.encode(uuid)).equals(uuid))
				.check()
				.assertIsSatisfied();
	}

}