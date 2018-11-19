package es.danielcr86.flightSearch;

import java.util.stream.Stream;

public interface FlightSource {

	Stream<Flight> getFlights();

}
