package es.danielcr86.flightSearch;

import static es.danielcr86.flightSearch.flightPredicates.RouteIs.routeIs;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FlightSearch {

	private FilteredFlightSource flightSource;
	private PricingSource pricingSource;

	public FlightSearch(FilteredFlightSource flightSource, PricingSource pricingSource) {
		this.flightSource = flightSource;
		this.pricingSource = pricingSource;
	}

	public List<FlightSearchResult> search(String origin, String destination, int passengers, LocalDate departureDate)
			throws WrongNumberOfPassengersException, DateInThePastException {

		return searchAsStream(origin, destination, passengers, departureDate).collect(Collectors.toList());
	}

	public Stream<FlightSearchResult> searchAsStream(String origin, String destination, int passengers, LocalDate departureDate)
			throws WrongNumberOfPassengersException, DateInThePastException {

		if (passengers <= 0) {
			throw new WrongNumberOfPassengersException(passengers);
		}
		if (departureDate.isBefore(LocalDate.now())) {
			throw new DateInThePastException(departureDate);
		}
		PriceModifier priceModifier = new DepartureDateBasedPriceModifier(departureDate);
		return flightSource.getFlights(routeIs(origin, destination))
				.map(flight -> new FlightSearchResult(flight.getCode()))
				.peek(result -> setPriceInFlightSearchResult(result, passengers, priceModifier));
	}

	private void setPriceInFlightSearchResult(FlightSearchResult result, int passengers, PriceModifier priceModifier) {
		Optional<BigDecimal> basePrice = pricingSource.getPrice(result.getFlightCode());
		basePrice.ifPresent(basePriceValue -> {
			BigDecimal finalPrice = priceModifier.modifyPrice(basePriceValue);
			result.setPrice(finalPrice.multiply(BigDecimal.valueOf(passengers).setScale(2, BigDecimal.ROUND_HALF_UP)));
		});
	}

}
