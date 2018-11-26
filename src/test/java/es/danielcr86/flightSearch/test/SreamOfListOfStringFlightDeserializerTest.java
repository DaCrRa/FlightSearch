package es.danielcr86.flightSearch.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertThat;

import es.danielcr86.flightSearch.Flight;
import es.danielcr86.flightSearch.flightSources.StreamOfListOfStringFlightDeserializer;

public class SreamOfListOfStringFlightDeserializerTest {

	@Test
	public void whenAllListsHaveRequiredNumberOfStrings_thenAsManyFlightsAsListsAreCreated() {
		StreamOfListOfStringFlightDeserializer deserializer = mock(StreamOfListOfStringFlightDeserializer.class,
				Mockito.CALLS_REAL_METHODS);
		when(deserializer.readFlightsData()).thenReturn(Stream.of(
				Arrays.asList("origin1", "dest1", "code1"),
				Arrays.asList("origin2", "dest2", "code2"),
				Arrays.asList("origin3", "dest3", "code3")
				));

		List<Flight> deserializedFlights = deserializer.getFlights().collect(Collectors.toList());

		assertThat(deserializedFlights, containsInAnyOrder(
				new Flight("origin1", "dest1", "code1"),
				new Flight("origin2", "dest2", "code2"),
				new Flight("origin3", "dest3", "code3")
				));
	}

	@Test
	public void whenOneListHasLessStringsThanRequired_thenListIsSkipped() {
		StreamOfListOfStringFlightDeserializer deserializer = mock(StreamOfListOfStringFlightDeserializer.class,
				Mockito.CALLS_REAL_METHODS);
		when(deserializer.readFlightsData()).thenReturn(Stream.of(
				Arrays.asList("origin1", "dest1", "code1"),
				Arrays.asList("origin2", "dest2"),
				Arrays.asList("origin3", "dest3", "code3")
				));

		List<Flight> deserializedFlights = deserializer.getFlights().collect(Collectors.toList());

		assertThat(deserializedFlights, containsInAnyOrder(
				new Flight("origin1", "dest1", "code1"),
				new Flight("origin3", "dest3", "code3")
				));
	}

	@Test
	public void whenOneListHasMoreStringsThanRequired_thenFlightCreatedWithFirstFields() {
		StreamOfListOfStringFlightDeserializer deserializer = mock(StreamOfListOfStringFlightDeserializer.class,
				Mockito.CALLS_REAL_METHODS);
		when(deserializer.readFlightsData()).thenReturn(Stream.of(
				Arrays.asList("origin1", "dest1", "code1"),
				Arrays.asList("origin2", "dest2", "code2", "extraStuff"),
				Arrays.asList("origin3", "dest3", "code3")
				));

		List<Flight> deserializedFlights = deserializer.getFlights().collect(Collectors.toList());

		assertThat(deserializedFlights, containsInAnyOrder(
				new Flight("origin1", "dest1", "code1"),
				new Flight("origin2", "dest2", "code2"),
				new Flight("origin3", "dest3", "code3")
				));
	}

	@Test
	public void whenOneListHasMoreStringsThanRequiredAndOneHasLess_thenListWithLessFieldsIsSkipped() {
		StreamOfListOfStringFlightDeserializer deserializer = mock(StreamOfListOfStringFlightDeserializer.class,
				Mockito.CALLS_REAL_METHODS);
		when(deserializer.readFlightsData()).thenReturn(Stream.of(
				Arrays.asList("origin1", "dest1"),
				Arrays.asList("origin2", "dest2", "code2", "extraStuff"),
				Arrays.asList("origin3", "dest3", "code3")
				));

		List<Flight> deserializedFlights = deserializer.getFlights().collect(Collectors.toList());

		assertThat(deserializedFlights, containsInAnyOrder(
				new Flight("origin2", "dest2", "code2"),
				new Flight("origin3", "dest3", "code3")
				));
	}

	@Test
	public void whenStreamIsEmpty_thenListOfFlightIsEmpty() {
		StreamOfListOfStringFlightDeserializer deserializer = mock(StreamOfListOfStringFlightDeserializer.class,
				Mockito.CALLS_REAL_METHODS);
		when(deserializer.readFlightsData()).thenReturn(Stream.of());

		List<Flight> deserializedFlights = deserializer.getFlights().collect(Collectors.toList());

		assertThat(deserializedFlights, empty());
	}
}
