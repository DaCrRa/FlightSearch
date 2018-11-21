package es.danielcr86.flightSearch.test;

import static org.junit.Assert.assertThat;
import static es.danielcr86.flightSearch.flightPredicates.RouteIs.routeIs;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import es.danielcr86.flightSearch.FilteredFlightSource;
import es.danielcr86.flightSearch.Flight;
import es.danielcr86.flightSearch.FlightSource;

public class FlightFilteringTest {

	private FlightSource flightSourceWithFlights;

	@Before
	public void configureSearchEngine()
	{
		flightSourceWithFlights = mock(FlightSource.class);
		when(flightSourceWithFlights.getFlights()).thenReturn(Stream.of(
				new Flight("BCN", "MAD", "IBxxxxx"),
				new Flight("ARN", "MAD", "IByyyyy"),
				new Flight("ARN", "MAD", "IBaaaaa"),
				new Flight("ARN", "MAD", "LHyyyyy"),
				new Flight("NYO", "BCN", "IBzzzzz"),
				new Flight("MAD", "BCN", "IBnnnnn")
				));
	}

	@Test
	public void givenEmptyFlightSource_whenGetFlightsWithAFilter_thenReturnsEmptyFlightStream()
	{
		FlightSource emptyFlightSource = mock(FlightSource.class);
		when(emptyFlightSource.getFlights()).thenReturn(Stream.of());

		FilteredFlightSource filteredflightSource = new FilteredFlightSource(emptyFlightSource);

		List<Flight> listOfFilteredFlights = filteredflightSource.getFlights(routeIs("MAD", "BCN")).collect(Collectors.toList());

		assertThat(listOfFilteredFlights, empty());
	}

	@Test
	public void givenFlightSourceWithOneFlight_whenGetFlightsWithAFilterMatchingOne_thenReturnsFlightStreamWithTheFlight()
	{
		FlightSource emptyFlightSource = mock(FlightSource.class);
		when(emptyFlightSource.getFlights()).thenReturn(Stream.of(
				new Flight("MAD", "BCN", "IBnnnnn")
				));

		FilteredFlightSource filteredflightSource = new FilteredFlightSource(emptyFlightSource);

		List<Flight> listOfFilteredFlights = filteredflightSource.getFlights(routeIs("MAD", "BCN")).collect(Collectors.toList());

		assertThat(listOfFilteredFlights, containsInAnyOrder(new Flight("MAD", "BCN", "IBnnnnn")));
	}

	@Test
	public void givenFlightSourceWithSomeFlights_whenGetFlightsWithAFilterMatchingOne_thenReturnsFlightStreamWithTheFlight()
	{
		FilteredFlightSource filteredflightSource = new FilteredFlightSource(flightSourceWithFlights);

		List<Flight> listOfFilteredFlights = filteredflightSource.getFlights(routeIs("MAD", "BCN")).collect(Collectors.toList());

		assertThat(listOfFilteredFlights, containsInAnyOrder(new Flight("MAD", "BCN", "IBnnnnn")));
	}

	@Test
	public void givenFlightSourceWithSomeFlights_whenGetFlightsWithAFilterMatchingMoreThanOne_thenReturnsFlightStreamWithTheFlights()
	{
		FilteredFlightSource filteredflightSource = new FilteredFlightSource(flightSourceWithFlights);

		List<Flight> listOfFilteredFlights = filteredflightSource.getFlights(routeIs("ARN", "MAD")).collect(Collectors.toList());

		assertThat(listOfFilteredFlights, containsInAnyOrder(
				new Flight("ARN", "MAD", "IByyyyy"),
				new Flight("ARN", "MAD", "IBaaaaa"),
				new Flight("ARN", "MAD", "LHyyyyy")
				));
	}

	@Test
	public void givenFlightSourceWithSomeFlights_whenGetFlightsWithAFilterNotMatchingAny_thenReturnsEmptyFlightStream()
	{
		FilteredFlightSource filteredflightSource = new FilteredFlightSource(flightSourceWithFlights);

		List<Flight> listOfFilteredFlights = filteredflightSource.getFlights(routeIs("MAD", "ARN")).collect(Collectors.toList());

		assertThat(listOfFilteredFlights, empty());
	}
}
