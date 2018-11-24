package es.danielcr86.flightSearch;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.lastminute.CsvFiles;

public class CsvFilePricingSource implements PricingSource {

	private String csvFilePath;

	public CsvFilePricingSource(String pathToCsv) {
		csvFilePath = pathToCsv;
	}

	@Override
	public BigDecimal getPrice(String flightCode) {
		Optional<List<String>> priceEntry = CsvFiles.records(csvFilePath)
				.filter(list -> {
					return list.get(0).equals(flightCode);
				})
				.findFirst();
		if (priceEntry.isPresent()) {
			return BigDecimal.valueOf(Double.parseDouble(priceEntry.get().get(1)));
		}
		return null;
	}

}
