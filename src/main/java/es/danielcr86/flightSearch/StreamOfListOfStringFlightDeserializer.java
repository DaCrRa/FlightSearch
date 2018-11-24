package es.danielcr86.flightSearch;

import java.util.List;
import java.util.stream.Stream;

public abstract class StreamOfListOfStringFlightDeserializer implements FlightSource {

	public abstract Stream<List<String>> readFlightsData(); 

	@Override
	public Stream<Flight> getFlights() {
		return readFlightsData().map(this::listOfStringToFlight).filter(fligth -> fligth != null);
	}

	private Flight listOfStringToFlight(List<String> flightFields) {
		Flight flight = null;
		if (flightFields.size() >= 3) {
			flight = new Flight(flightFields.get(0), flightFields.get(1), flightFields.get(2));
		}
		return flight;
	}
}
