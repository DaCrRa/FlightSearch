package es.danielcr86.flightSearch;

import java.util.stream.Stream;

import com.lastminute.CsvFiles;

public class CsvFileFlightSource implements FlightSource {

	private String csvFilePath;

	public CsvFileFlightSource(String pathToCsv) {
		csvFilePath = pathToCsv;
	}

	@Override
	public Stream<Flight> getFlights() {
		return CsvFiles.records(csvFilePath).map(entryFields->{
			Flight flight = null;
			if (entryFields.size() >= 3) {
				flight = new Flight(entryFields.get(0), entryFields.get(1), entryFields.get(2));
			}
			return flight;
		}).filter(fligth -> fligth != null);
	}

}
