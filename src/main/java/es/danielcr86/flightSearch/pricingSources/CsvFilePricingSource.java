package es.danielcr86.flightSearch.pricingSources;

import java.util.List;
import java.util.stream.Stream;

import com.lastminute.CsvFiles;

public class CsvFilePricingSource extends StreamOfListOfStringPriceDeserializer {

	private String csvFilePath;

	public CsvFilePricingSource(String pathToCsv) {
		csvFilePath = pathToCsv;
	}

	@Override
	public Stream<List<String>> readPriceData() {
		return CsvFiles.records(csvFilePath);
	}

}
