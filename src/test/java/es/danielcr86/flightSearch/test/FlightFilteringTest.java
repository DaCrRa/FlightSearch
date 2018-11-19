package es.danielcr86.flightSearch.test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import es.danielcr86.flightSearch.FilteredFlightSource;
import es.danielcr86.flightSearch.Flight;
import es.danielcr86.flightSearch.FlightSource;
import es.danielcr86.flightSearch.RouteFlightFilter;

public class FlightFilteringTest {

	@Test
	public void givenEmptyFlightSource_whenGetFligthsWithAFilter_thenReturnsEmptyFlightStream()
	{
		FlightSource flightSource = mock(FlightSource.class);
		when(flightSource.getFlights()).thenReturn(Stream.of());

		FilteredFlightSource filteredflightSource = new FilteredFlightSource(flightSource);
		
		RouteFlightFilter filter = new RouteFlightFilter("MAD", "BCN");

		List<Flight> listOfFilteredFlights = filteredflightSource.getFlights(filter).collect(Collectors.toList());

		assertThat(listOfFilteredFlights, empty());
	}
}
