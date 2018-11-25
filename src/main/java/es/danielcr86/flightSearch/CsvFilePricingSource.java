package es.danielcr86.flightSearch;

import java.math.BigDecimal;
import java.util.Optional;

import com.lastminute.CsvFiles;

public class CsvFilePricingSource implements PricingSource {

	private String csvFilePath;

	public CsvFilePricingSource(String pathToCsv) {
		csvFilePath = pathToCsv;
	}

	@Override
	public Optional<BigDecimal> getPrice(String flightCode) {
		return CsvFiles.records(csvFilePath)
				.filter(list -> {
					return list.get(0).equals(flightCode);
				})
				.findFirst()
				.map(priceEntry -> {
					return BigDecimal.valueOf(Double.parseDouble(priceEntry.get(1)));
				});
	}

}
