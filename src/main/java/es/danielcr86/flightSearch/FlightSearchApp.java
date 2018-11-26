package es.danielcr86.flightSearch;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FlightSearchApp {

	public static void main(String[] args) {
		if (args.length < 4) {
			usage();
			System.exit(1);
		}

		String origin = args[0];
		String destination = args[1];

		int passengers = -1;
		try {
			passengers = Integer.parseUnsignedInt(args[2]);
		} catch (NumberFormatException e) {
			System.err.println("Wrong number of passengers: " + args[2]);
			usage();
			System.exit(2);
		}

		LocalDate departureDate = null;
		try {
			departureDate = LocalDate.parse(args[3], DateTimeFormatter.BASIC_ISO_DATE);
		} catch (DateTimeParseException e) {
			System.err.println(e.getMessage());
			System.err.println("Wrong departure date provided: " + args[3]);
			usage();
			System.exit(3);
		}

		FilteredFlightSource filteredFlightSource = new FilteredFlightSource(new CsvFileFlightSource(fullPathTo("flight-routes.csv")));
		PricingSource pricingSource = new CsvFilePricingSource(fullPathTo("flight-prices.csv"));
		FlightSearch searchEngine = new FlightSearch(filteredFlightSource, pricingSource);

		System.out.println("SEARCH RESULTS");
		System.out.println("==============");
		System.out.println("From: " + origin + "     To: " + destination + "     Passengers: " + passengers + "     Date: " + departureDate);

		try {
			searchEngine.searchAsStream(origin, destination, passengers, departureDate).forEach(System.out::println);
		} catch (WrongNumberOfPassengersException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void usage() {
		System.err.println(
				"Argument 1: origin" + "\n" +
				"Argument 2: destination" + "\n" +
				"Argument 3: departure date (yyyymmdd)"
				);
	}

	private static String fullPathTo(String fileName) {
		try {
			return Paths.get(FlightSearchApp.class.getClassLoader().getResource(fileName).toURI()).toString();
		} catch (URISyntaxException e) {
			// Quite unlikely
			throw new RuntimeException("ClassLoader.getResource(Sting) returned an URL not compliant with URI syntax.");
		}
	}

}
