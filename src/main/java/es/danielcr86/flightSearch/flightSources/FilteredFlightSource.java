package es.danielcr86.flightSearch.flightSources;

import java.util.function.Predicate;
import java.util.stream.Stream;

import es.danielcr86.flightSearch.Flight;

public class FilteredFlightSource {

	FlightSource flightSource;

	public FilteredFlightSource(FlightSource flightSource) {
		this.flightSource = flightSource;
	}

	public Stream<Flight> getFlights(Predicate<Flight> filter) {
		return flightSource.getFlights().filter(filter);
	}
}
