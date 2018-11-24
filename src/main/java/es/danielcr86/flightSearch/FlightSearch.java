package es.danielcr86.flightSearch;

import static es.danielcr86.flightSearch.flightPredicates.RouteIs.routeIs;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class FlightSearch {

	private FilteredFlightSource flightSource;
	private PricingSource pricingSource;

	public FlightSearch(FilteredFlightSource flightSource) {
		this.flightSource = flightSource;
	}

	public List<FlightSearchResult> search(String origin, String destination, int passengers, int daysTillDeparture) {
		return flightSource.getFlights(routeIs(origin, destination))
				.map(flight -> new FlightSearchResult(flight.getCode()))
				.peek(this::setPriceInFlightSearchResult)
				.collect(Collectors.toList());
	}

	private void setPriceInFlightSearchResult(FlightSearchResult result) {
		BigDecimal price = pricingSource.getPrice(result.getFlightCode());
		result.setPrice(price);
	}

}
