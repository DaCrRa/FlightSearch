package es.danielcr86.flightSearch;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class FilteredFlightSource {

	FlightSource flightSource;

	public FilteredFlightSource(FlightSource flightSource) {
		this.flightSource = flightSource;
	}

	public Stream<Flight> getFlights(Predicate<Flight> filter) {
		return flightSource.getFlights().filter(filter);
	}
}
