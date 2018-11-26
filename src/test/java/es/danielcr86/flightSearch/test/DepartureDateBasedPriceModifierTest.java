package es.danielcr86.flightSearch.test;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;

import es.danielcr86.flightSearch.priceModifiers.DepartureDateBasedPriceModifier;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class DepartureDateBasedPriceModifierTest {

	private final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100).setScale(2);

	@Test
	@Parameters({
		"40, 80",
		"35, 80",
		"31, 80",
		"30, 100",
		"25, 100",
		"20, 100",
		"16, 100",
		"15, 120",
		"10, 120",
		"7 , 120",
		"3 , 120",
		"2 , 150",
		"1 , 150",
		"0 , 150"
	})
	public void givenDaysTillDeparture_whenModifyingPriceForTheDate_thenReturnsCorrectModification(int daysTillDeparture, double expectedPrice) {
		LocalDate departureDate = LocalDate.now().plus(daysTillDeparture, DAYS);

		DepartureDateBasedPriceModifier priceModifier = new DepartureDateBasedPriceModifier(departureDate);

		assertThat(priceModifier.modifyPrice(ONE_HUNDRED), is(BigDecimal.valueOf(expectedPrice).setScale(2, BigDecimal.ROUND_HALF_UP)));
	}
}
