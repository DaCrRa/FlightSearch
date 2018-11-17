package es.danielcr86.flightSearch;

import java.util.Arrays;
import java.util.List;

public class FlightSearch {

	public List<FlightSearchResult> search(String origin, String destination) {
		return Arrays.asList(
				new FlightSearchResult("TK2372"),
				new FlightSearchResult("TK2659"),
				new FlightSearchResult("LH5909")
				);
	}

}
