package es.danielcr86.flightSearch;

import java.math.BigDecimal;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;

public class DepartureDateBasedPriceModifier implements PriceModifier {

	private long daysTillDeparture;

	public DepartureDateBasedPriceModifier(LocalDate departureDate) {
		this.daysTillDeparture = DAYS.between(LocalDate.now(), departureDate);
	}

	@Override
	public BigDecimal modifyPrice(BigDecimal basePrice) {
		BigDecimal modifiedPrice;
		if (daysTillDeparture >= 31) {
			modifiedPrice = basePrice.multiply(BigDecimal.valueOf(0.8));
		} else if (daysTillDeparture >= 16) {
			modifiedPrice = basePrice;
		} else if (daysTillDeparture >= 3) {
			modifiedPrice = basePrice.multiply(BigDecimal.valueOf(1.2));
		} else {
			modifiedPrice = basePrice.multiply(BigDecimal.valueOf(1.5));
		}
		return modifiedPrice;
	}

}
