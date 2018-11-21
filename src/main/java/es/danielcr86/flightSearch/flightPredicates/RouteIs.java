package es.danielcr86.flightSearch.flightPredicates;

import java.util.function.Predicate;

import es.danielcr86.flightSearch.Flight;

public class RouteIs {

	public static Predicate<Flight> routeIs(String from, String to) {
		return new Predicate<Flight>() {
			@Override
			public boolean test(Flight flight) {
				return flight.getOrigin().equals(from) &&
						flight.getDestination().equals(to);
			}
		};
	}
}
