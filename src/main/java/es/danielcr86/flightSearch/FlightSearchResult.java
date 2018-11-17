package es.danielcr86.flightSearch;

public class FlightSearchResult {

	private String flightCode;

	public FlightSearchResult(String flightCode) {
		this.flightCode = flightCode;
	}

	@Override
	public boolean equals(Object otherResult) {
		return otherResult != null &&
				otherResult instanceof FlightSearchResult &&
				((FlightSearchResult) otherResult).flightCode.equals(this.flightCode);
	}

	@Override
	public String toString() {
		return this.flightCode;
	}
}
