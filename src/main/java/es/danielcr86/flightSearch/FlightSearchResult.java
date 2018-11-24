package es.danielcr86.flightSearch;

import java.math.BigDecimal;

public class FlightSearchResult {

	private String flightCode;
	private BigDecimal price;

	public FlightSearchResult(String flightCode) {
		this(flightCode, BigDecimal.ZERO);
	}

	public FlightSearchResult(String flightCode, BigDecimal price) {
		this.flightCode = flightCode;
		this.price = price;
	}

	@Override
	public boolean equals(Object otherResult) {
		if (otherResult != null && otherResult instanceof FlightSearchResult) {
			FlightSearchResult other = (FlightSearchResult) otherResult;
			return this.flightCode == other.flightCode &&
					this.price.compareTo(other.price) == 0;
		}
		return false;
	}

	@Override
	public String toString() {
		return this.flightCode + "  " + price.toString();
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getFlightCode() {
		return flightCode;
	}
}
