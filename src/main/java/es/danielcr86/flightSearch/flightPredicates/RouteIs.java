package es.danielcr86.flightSearch.flightPredicates;

import java.util.function.Predicate;

import es.danielcr86.flightSearch.Flight;

public class RouteIs {

	public static Predicate<Flight> routeIs(String from, String to) {
		return new Predicate<Flight>() {
			@Override
			public boolean test(Flight t) {
				// TODO Auto-generated method stub

				System.out.println(from);
				System.out.println(to);
				return false;
			}
		};
	}
}
