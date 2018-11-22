package es.danielcr86.flightSearch.e2e;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsEmptyCollection.empty;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

import es.danielcr86.flightSearch.FlightSearch;
import es.danielcr86.flightSearch.FlightSearchResult;
import es.danielcr86.flightSearch.CsvFileFlightSource;
import es.danielcr86.flightSearch.FilteredFlightSource;

public class FlightSearchE2ETest {

	private static FlightSearch searchEngine;

	@BeforeClass
	public static void configureSearchEngine()
	{
		searchEngine = new FlightSearch(
				new FilteredFlightSource(
						new CsvFileFlightSource(fullPathTo("flight-routes.csv")
								)
						)
				);
	}

	private static String fullPathTo(String fileName) {
		try {
			return Paths.get(FlightSearchE2ETest.class.getClassLoader().getResource(fileName).toURI()).toString();
		} catch (URISyntaxException e) {
			// Quite unlikely
			throw new RuntimeException("ClassLoader.getResource(Sting) returned an URL not compliant with URI syntax.");
		}
	}

	/**
	 *  1 passenger, 31 days to the departure date, flying AMS -> FRA
	 *
	 * flights: 
	 * TK2372, 157.6 €
	 * TK2659, 198.4 €
	 * LH5909, 90.4 €
	 */
	@Test
	public void fromAMS_toFRA_1passenger_31days()
	{
		List<FlightSearchResult> results = searchEngine.search("AMS", "FRA");

		assertThat(results, containsInAnyOrder(
				new FlightSearchResult("TK2372"),
				new FlightSearchResult("TK2659"),
				new FlightSearchResult("LH5909")
				));
	}

	/**
	 *  3 passengers, 15 days to the departure date, flying LHR -> IST
	 *  
	 * flights:
	 * TK8891, 900 € (3 * (120% of 250))
	 * LH1085, 532.8 € (3 * (120% of 148))
	 */
	@Test
	public void fromLHR_toIST_3passengers_15days()
	{
		List<FlightSearchResult> results = searchEngine.search("LHR", "IST");

		assertThat(results, containsInAnyOrder(
				new FlightSearchResult("TK8891"),
				new FlightSearchResult("LH1085")
				));
	}

	/**
	 * 2 passengers, 2 days to the departure date, flying BCN -> MAD
	 *
	 * flights:
	 * IB2171, 777 € (2 * (150% of 259))
	 * LH5496, 879 € (2 * (150% of 293))
	 */
	@Test
	public void fromBCN_toMAD_2passengers_2days()
	{
		List<FlightSearchResult> results = searchEngine.search("BCN", "MAD");

		assertThat(results, containsInAnyOrder(
				new FlightSearchResult("IB2171"),
				new FlightSearchResult("LH5496")
				));
	}

	/**
	 * CDG -> FRA
	 * 
	 * no flights available
	 */
	@Test
	public void fromCDG_toFRA()
	{
		List<FlightSearchResult> results = searchEngine.search("CDG", "FRA");

		assertThat(results, empty());
	}
}
