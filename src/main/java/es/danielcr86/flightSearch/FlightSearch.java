package es.danielcr86.flightSearch;

import static es.danielcr86.flightSearch.flightPredicates.RouteIs.routeIs;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FlightSearch {

	private FilteredFlightSource flightSource;
	private PricingSource pricingSource;

	public FlightSearch(FilteredFlightSource flightSource, PricingSource pricingSource) {
		this.flightSource = flightSource;
		this.pricingSource = pricingSource;
	}

	public List<FlightSearchResult> search(String origin, String destination, int passengers, int daysTillDeparture) {
		return flightSource.getFlights(routeIs(origin, destination))
				.map(flight -> new FlightSearchResult(flight.getCode()))
				.peek(result -> setPriceInFlightSearchResult(result, passengers, daysTillDeparture))
				.collect(Collectors.toList());
	}

	private void setPriceInFlightSearchResult(FlightSearchResult result, int passengers, int daysTillDeparture) {
		PriceModifier priceModifier = new DepartureDateBasedPriceModifier(daysTillDeparture);
		Optional<BigDecimal> basePrice = pricingSource.getPrice(result.getFlightCode());
		basePrice.ifPresent(basePriceValue -> {
			BigDecimal finalPrice = priceModifier.modifyPrice(basePriceValue);
			result.setPrice(finalPrice.multiply(BigDecimal.valueOf(passengers)));
		});
	}

}
