package es.danielcr86.flightSearch;

public class WrongNumberOfPassengersException extends Exception {

	public WrongNumberOfPassengersException(int passengers) {
		super("Number of passengers must be >= 1, but provided: " + passengers);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -5648808707895826179L;

}
