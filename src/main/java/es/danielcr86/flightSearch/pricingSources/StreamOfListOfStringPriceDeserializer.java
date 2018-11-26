package es.danielcr86.flightSearch.pricingSources;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class StreamOfListOfStringPriceDeserializer implements PricingSource {

	@Override
	public Optional<BigDecimal> getPrice(String flightCode) {
		return getPriceEntry(flightCode)
				.map(priceEntry -> {
					try {
						return BigDecimal.valueOf(Double.parseDouble(priceEntry.get(1)));
					} catch (IndexOutOfBoundsException e) {
						return null;
					} catch (NumberFormatException e) {
						return null;
					}
				});
	}

	public abstract Stream<List<String>> readPriceData();

	public Optional<List<String>> getPriceEntry(String flightCode) {
		return readPriceData().filter(entry -> {
			try {
				return entry.get(0).equals(flightCode);
			} catch (IndexOutOfBoundsException e) {
				return false;
			}
		}).findFirst();
	}
}
