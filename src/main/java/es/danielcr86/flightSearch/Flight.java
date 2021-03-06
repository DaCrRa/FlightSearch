package es.danielcr86.flightSearch;

public class Flight {

	private String origin;
	private String destination;
	private String code;

	public Flight(String origin, String destination, String code) {
		this.origin = origin;
		this.destination = destination;
		this.code = code;
	}

	@Override
	public boolean equals(Object otherFlight) {
		if (otherFlight != null && otherFlight instanceof Flight) {
			Flight other = (Flight) otherFlight;
			return this.origin.equals(other.origin) &&
					this.destination.equals(other.destination) &&
					this.code.equals(other.code);
		}
		return false;
	}

	@Override
	public String toString() {
		return code + ": " + origin + " -> " + destination;
	}

	public String getOrigin() {
		return origin;
	}

	public String getDestination() {
		return destination;
	}

	public String getCode() {
		return code;
	}
}
