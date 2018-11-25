package es.danielcr86.flightSearch.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import es.danielcr86.flightSearch.StreamOfListOfStringPriceDeserializer;

public class SreamOfListOfStringPriceDeserializerTest {

	private StreamOfListOfStringPriceDeserializer deserializer;

	@Before
	public void setUpDeserializer() {
		deserializer = mock(StreamOfListOfStringPriceDeserializer.class,
				Mockito.CALLS_REAL_METHODS);
	}

	@Test
	public void whenReadPriceDataReturnsEmptyStream_thenGetPriceReturnsEmptyOptional() {
		when(deserializer.readPriceData()).thenReturn(Stream.of());

		Optional<BigDecimal> price = deserializer.getPrice("CODE");

		assertFalse(price.isPresent());
	}

	@Test
	public void whenReadPriceDataReturnsNoEntryForCode_thenGetPriceReturnsEmptyOptional() {
		when(deserializer.readPriceData()).thenReturn(Stream.of(
				Arrays.asList("CODE", "100")
				));

		Optional<BigDecimal> price = deserializer.getPrice("NOTPRESENTCODE");

		assertFalse(price.isPresent());
	}

	@Test
	public void whenReadPriceDataReturnsEntryWithNoPriceField_thenGetPriceReturnsEmptyOptional() {
		when(deserializer.readPriceData()).thenReturn(Stream.of(
				Arrays.asList("CODE")
				));

		Optional<BigDecimal> price = deserializer.getPrice("CODE");

		assertFalse(price.isPresent());
	}

	@Test
	public void whenReadPriceDataReturnsEntryWithWrongField_thenGetPriceReturnsEmptyOptional() {
		when(deserializer.readPriceData()).thenReturn(Stream.of(
				Arrays.asList("CODE", "not-a-number")
				));

		Optional<BigDecimal> price = deserializer.getPrice("CODE");

		assertFalse(price.isPresent());
	}

	@Test
	public void whenReadPriceDataReturnsEntryWithCorrectPrice_thenGetPriceReturnsOptionalWithValue() {
		when(deserializer.readPriceData()).thenReturn(Stream.of(
				Arrays.asList("CODE", "100")
				));

		Optional<BigDecimal> price = deserializer.getPrice("CODE");

		assertTrue(price.isPresent());
		assertThat(price.get().doubleValue(), closeTo(100, 0.001));
	}

	@Test
	public void whenReadPriceDataReturnsCorrectEntries_thenGetPriceReturnsOptionalWithValue() {
		when(deserializer.readPriceData()).thenReturn(Stream.of(
				Arrays.asList("CODE1", "100"),
				Arrays.asList("CODE2", "200"),
				Arrays.asList("CODE3", "300")
				));

		Optional<BigDecimal> price = deserializer.getPrice("CODE1");

		assertTrue(price.isPresent());
		assertThat(price.get().doubleValue(), closeTo(100, 0.001));
	}
}
