package com.devskiller.friendly_id;

import java.math.BigInteger;
import java.net.URI;
import java.util.UUID;

import org.junit.Test;

import static com.devskiller.friendly_id.IdUtil.areEqualIgnoringLeadingZeros;
import static io.vavr.test.Property.def;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.util.Objects.areEqual;

public class Url62Test {

	@Test
	public void encodingUuidShouldBeReversible() {
		def("areEqual(Url62.decode(Url62.encode(uuid)), uuid)")
				.forAll(DataProvider.UUIDS)
				.suchThat(uuid -> areEqual(Url62.decode(Url62.encode(uuid)), uuid))
				.check(-1, 1000000)
				.assertIsSatisfied();
	}

	@Test
	public void decodingIdShouldBeReversible() throws Exception {
		def("areEqualIgnoringLeadingZeros(Url62.encode(Url62.decode(id)), id)")
				.forAll(DataProvider.FRIENDLY_IDS)
				.suchThat(id -> areEqualIgnoringLeadingZeros(Url62.encode(Url62.decode(id)), id))
				.check(100, 100000)
				.assertIsSatisfied();
	}

	@Test
	public void shouldExplodeWhenContainsIllegalCharacters() throws Exception {
		assertThatThrownBy(() -> Url62.decode("Foo Bar"))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("contains illegal characters");
	}

	@Test
	public void shouldFaildOnEmpyString() throws Exception {
		assertThatThrownBy(() -> Url62.decode(""))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("must not be empty");
	}

	@Test
	public void shouldFaildOnNullString() throws Exception {
		assertThatThrownBy(() -> Url62.decode(null))
				.isInstanceOf(NullPointerException.class)
				.hasMessageContaining("must not be null");
	}

	@Test
	public void shouldFaildWhenStringContainsMoreThan128bitInformation() throws Exception {
		assertThatThrownBy(() -> Url62.decode("7NLCAyd6sKR7kDHxgAWFPas"))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("more than 128bit information");
	}

	@Test
	public void name() throws Exception {
		Url62.decode("0");
		BigInteger v = BigInteger.valueOf(Long.MAX_VALUE).multiply(BigInteger.valueOf(Long.MAX_VALUE));
		System.out.println(Url62.decode("0"));
		UUID uuid = new UUID(Long.MAX_VALUE, Long.MAX_VALUE);
		System.out.println(uuid);

		System.out.println("340282366920936045556395145956585308163");
		System.out.println(v);
		UUID max = UUID.fromString("7fffffff-ffff-7fff-ffff-ffffffffffff");
//		System.out.println(max);
//		System.out.println(ElegantPairing.pair(BigInteger.valueOf(max.getMostSignificantBits()), BigInteger.valueOf(max.getLeastSignificantBits())));
//		System.out.println(Url62.decode(Url62.encode(UUID.fromString("8fffffff-ffff-ffff-ffff-ffffffffffff"))));
		System.out.println(Base62.encode(v));
		System.out.println(Url62.decode(Base62.encode(v)));
		System.out.println(Url62.encode(UUID.fromString("00000000-0000-0000-c000-000000000000")));
	}
}