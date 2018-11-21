package es.danielcr86.flightSearch;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class FilteredFlightSource {

	public FilteredFlightSource(FlightSource flightSource) {
		// TODO Auto-generated constructor stub
	}

	public Stream<Flight> getFlights(Predicate<Flight> filter) {
		return flightSource.getFlights();
	}
}
