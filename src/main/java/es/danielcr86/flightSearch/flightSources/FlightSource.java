package es.danielcr86.flightSearch.flightSources;

import java.util.stream.Stream;

import es.danielcr86.flightSearch.Flight;

public interface FlightSource {

	Stream<Flight> getFlights();

}
