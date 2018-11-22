package es.danielcr86.flightSearch;

import static es.danielcr86.flightSearch.flightPredicates.RouteIs.routeIs;

import java.util.List;
import java.util.stream.Collectors;

public class FlightSearch {

	private FilteredFlightSource flightSource;

	public FlightSearch(FilteredFlightSource flightSource) {
		this.flightSource = flightSource;
	}

	public List<FlightSearchResult> search(String origin, String destination) {
		return flightSource.getFlights(routeIs(origin, destination))
				.map(flight -> new FlightSearchResult(flight.getCode()))
				.collect(Collectors.toList());
	}

}
