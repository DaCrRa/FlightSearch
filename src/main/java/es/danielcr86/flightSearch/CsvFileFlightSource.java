package es.danielcr86.flightSearch;

import java.util.List;
import java.util.stream.Stream;

import com.lastminute.CsvFiles;

public class CsvFileFlightSource implements FlightSource {

	private String csvFilePath;

	public CsvFileFlightSource(String pathToCsv) {
		csvFilePath = pathToCsv;
	}

	@Override
	public Stream<Flight> getFlights() {
		return CsvFiles.records(csvFilePath).map(this::listOfStringToFlight).filter(fligth -> fligth != null);
	}

	private Flight listOfStringToFlight(List<String> flightFields) {
		Flight flight = null;
		if (flightFields.size() >= 3) {
			flight = new Flight(flightFields.get(0), flightFields.get(1), flightFields.get(2));
		}
		return flight;
	}
}
