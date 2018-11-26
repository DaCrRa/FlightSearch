package es.danielcr86.flightSearch;

import java.time.LocalDate;

public class DateInThePastException extends Exception {

	public DateInThePastException(LocalDate date) {
		super("Cannot search for flights in a past date: " + date);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2488184217803842643L;

}
