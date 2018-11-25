package es.danielcr86.flightSearch.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

import es.danielcr86.flightSearch.FlightSearchResult;

public class FlightSearchResultEqualsTest {

	@Test
	public void whenTwoResultsHaveDifferenCodeAndSamePrice_thenEqualsReturnsFalse() {
		FlightSearchResult result1 = new FlightSearchResult("CODE1", BigDecimal.valueOf(5));
		FlightSearchResult result2 = new FlightSearchResult("CODE2", BigDecimal.valueOf(5));

		assertThat(result1, not(equalTo(result2)));
	}

	@Test
	public void whenTwoResultsHaveDifferenCodeAndNoPriceSet_thenEqualsReturnsFalse() {
		FlightSearchResult result1 = new FlightSearchResult("CODE1");
		FlightSearchResult result2 = new FlightSearchResult("CODE2");

		assertThat(result1, not(equalTo(result2)));
	}

	@Test
	public void whenTwoResultsHaveDifferentPrices_thenEqualsReturnsFalse() {
		FlightSearchResult result1 = new FlightSearchResult("CODE", BigDecimal.valueOf(10));
		FlightSearchResult result2 = new FlightSearchResult("CODE", BigDecimal.valueOf(5));

		assertThat(result1, not(equalTo(result2)));
	}

	@Test
	public void whenOneResultHasNoPriceSet_thenEqualsReturnsFalse() {
		FlightSearchResult result1 = new FlightSearchResult("CODE", BigDecimal.valueOf(5));
		FlightSearchResult result2 = new FlightSearchResult("CODE");

		assertThat(result1, not(equalTo(result2)));
	}

	@Test
	public void whenTwoResultsHaveSameCodeAndNoPriceSet_thenEqualsReturnsTrue() {
		FlightSearchResult result1 = new FlightSearchResult("CODE");
		FlightSearchResult result2 = new FlightSearchResult("CODE");

		assertThat(result1, equalTo(result2));
	}

	@Test
	public void whenTwoResultsHaveSameCodeAndSamePrice_thenEqualsReturnsTrue() {
		FlightSearchResult result1 = new FlightSearchResult("CODE", BigDecimal.valueOf(5));
		FlightSearchResult result2 = new FlightSearchResult("CODE", BigDecimal.valueOf(5));

		assertThat(result1, equalTo(result2));
	}

}
