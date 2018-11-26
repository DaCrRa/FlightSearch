package es.danielcr86.flightSearch.e2e;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsEmptyCollection.empty;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.List;

import es.danielcr86.flightSearch.FlightSearch;
import es.danielcr86.flightSearch.FlightSearchResult;
import es.danielcr86.flightSearch.PricingSource;
import es.danielcr86.flightSearch.WrongNumberOfPassengersException;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import es.danielcr86.flightSearch.CsvFileFlightSource;
import es.danielcr86.flightSearch.CsvFilePricingSource;
import es.danielcr86.flightSearch.DateInThePastException;
import es.danielcr86.flightSearch.FilteredFlightSource;

@RunWith(JUnitParamsRunner.class)
public class FlightSearchE2ETest {

	private static FlightSearch searchEngine;

	@BeforeClass
	public static void configureSearchEngine()
	{
		FilteredFlightSource filteredFlightSource = new FilteredFlightSource(new CsvFileFlightSource(fullPathTo("flight-routes.csv")));
		PricingSource pricingSource = new CsvFilePricingSource(fullPathTo("flight-prices.csv"));
		searchEngine = new FlightSearch(filteredFlightSource, pricingSource);
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
	 * TK2372, 157.6 € (80% of 197)
	 * TK2659, 198.4 € (80% of 248)
	 * LH5909, 90.4 € (80% of 113)
	 * @throws WrongNumberOfPassengersException 
	 * @throws DateInThePastException 
	 */
	@Test
	public void fromAMS_toFRA_1passenger_31days() throws WrongNumberOfPassengersException, DateInThePastException
	{
		LocalDate _31DaysFromNow = LocalDate.now().plus(31, DAYS);

		List<FlightSearchResult> results = searchEngine.search("AMS", "FRA", 1, _31DaysFromNow);

		assertThat(results, containsInAnyOrder(
				new FlightSearchResult("TK2372", BigDecimal.valueOf( 0.8 * 197 ).setScale(2, BigDecimal.ROUND_HALF_UP)),
				new FlightSearchResult("TK2659", BigDecimal.valueOf( 0.8 * 248 ).setScale(2, BigDecimal.ROUND_HALF_UP)),
				new FlightSearchResult("LH5909", BigDecimal.valueOf( 0.8 * 113 ).setScale(2, BigDecimal.ROUND_HALF_UP))
				));
	}

	/**
	 *  3 passengers, 15 days to the departure date, flying LHR -> IST
	 *  
	 * flights:
	 * TK8891, 900 € (3 * (120% of 250))
	 * LH1085, 532.8 € (3 * (120% of 148))
	 * @throws WrongNumberOfPassengersException 
	 * @throws DateInThePastException 
	 */
	@Test
	public void fromLHR_toIST_3passengers_15days() throws WrongNumberOfPassengersException, DateInThePastException
	{
		LocalDate _15DaysFromNow = LocalDate.now().plus(15, DAYS);

		List<FlightSearchResult> results = searchEngine.search("LHR", "IST", 3, _15DaysFromNow);

		assertThat(results, containsInAnyOrder(
				new FlightSearchResult("TK8891", BigDecimal.valueOf(3 * (1.2 * 250) ).setScale(2, BigDecimal.ROUND_HALF_UP)),
				new FlightSearchResult("LH1085", BigDecimal.valueOf(3 * (1.2 * 148) ).setScale(2, BigDecimal.ROUND_HALF_UP))
				));
	}

	/**
	 * 2 passengers, 2 days to the departure date, flying BCN -> MAD
	 *
	 * flights:
	 * IB2171, 777 € (2 * (150% of 259))
	 * LH5496, 879 € (2 * (150% of 293))
	 * @throws WrongNumberOfPassengersException 
	 * @throws DateInThePastException 
	 */
	@Test
	public void fromBCN_toMAD_2passengers_2days() throws WrongNumberOfPassengersException, DateInThePastException
	{
		LocalDate _2DaysFromNow = LocalDate.now().plus(2, DAYS);

		List<FlightSearchResult> results = searchEngine.search("BCN", "MAD", 2, _2DaysFromNow);

		assertThat(results, containsInAnyOrder(
				new FlightSearchResult("IB2171", BigDecimal.valueOf(2 * (1.5 * 259) ).setScale(2, BigDecimal.ROUND_HALF_UP)),
				new FlightSearchResult("LH5496", BigDecimal.valueOf(2 * (1.5 * 293) ).setScale(2, BigDecimal.ROUND_HALF_UP))
				));
	}

	/**
	 * CDG -> FRA
	 * 
	 * no flights available
	 * @throws WrongNumberOfPassengersException 
	 * @throws DateInThePastException 
	 */
	@Test
	public void fromCDG_toFRA() throws WrongNumberOfPassengersException, DateInThePastException
	{
		LocalDate _5DaysFromNow = LocalDate.now().plus(5, DAYS);

		List<FlightSearchResult> results = searchEngine.search("CDG", "FRA", 3, _5DaysFromNow);

		assertThat(results, empty());
	}

	@Test(expected = WrongNumberOfPassengersException.class)
	@Parameters({"0", "-1", "-10"})
	public void wrongNumberOfPassengersThrowsException(int wrongNumberOfPassengers) throws WrongNumberOfPassengersException, DateInThePastException {
		searchEngine.search("origin", "destination", wrongNumberOfPassengers, LocalDate.now());
	}

	@Test(expected = DateInThePastException.class)
	public void dateInThePastThrowsException() throws WrongNumberOfPassengersException, DateInThePastException {
		searchEngine.search("origin", "destination", 2, LocalDate.now().minusMonths(5));
	}
}
