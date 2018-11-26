package es.danielcr86.flightSearch.flightSources;

import java.util.List;
import java.util.stream.Stream;

import com.lastminute.CsvFiles;

public class CsvFileFlightSource extends StreamOfListOfStringFlightDeserializer {

	private String csvFilePath;

	public CsvFileFlightSource(String pathToCsv) {
		csvFilePath = pathToCsv;
	}

	@Override
	public Stream<List<String>> readFlightsData() {
		return CsvFiles.records(csvFilePath);
	}
}
