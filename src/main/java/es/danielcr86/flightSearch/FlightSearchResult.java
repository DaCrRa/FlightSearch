package es.danielcr86.flightSearch;

import java.math.BigDecimal;
import java.util.Optional;

public class FlightSearchResult {

	private String flightCode;
	private Optional<BigDecimal> price;

	public FlightSearchResult(String flightCode) {
		this.flightCode = flightCode;
		this.price = Optional.empty();
	}

	public FlightSearchResult(String flightCode, BigDecimal price) {
		this.flightCode = flightCode;
		this.price = Optional.of(price);
	}

	@Override
	public boolean equals(Object otherResult) {
		if (otherResult != null && otherResult instanceof FlightSearchResult) {
			FlightSearchResult other = (FlightSearchResult) otherResult;
			boolean bothHaveSameCode = this.flightCode.equals(other.flightCode);

			boolean bothPricesAbsent = !(this.price.isPresent()) && !(other.price.isPresent());
			boolean bothPricesPresentWithSameValue = this.price.filter(thisPrice -> {
				return other.price.filter(otherPrice -> {
					return thisPrice.compareTo(otherPrice) == 0;
				}).isPresent();
			}).isPresent();

			return bothHaveSameCode && (bothPricesAbsent || bothPricesPresentWithSameValue);
		}
		return false;
	}

	@Override
	public String toString() {
		return this.flightCode + "  " + price.toString();
	}

	public void setPrice(BigDecimal price) {
		this.price = Optional.of(price);
	}

	public String getFlightCode() {
		return flightCode;
	}
}
