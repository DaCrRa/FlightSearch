package es.danielcr86.flightSearch;

import java.math.BigDecimal;

public class DepartureDateBasedPriceModifier implements PriceModifier {

	private int daysTillDeparture;

	public DepartureDateBasedPriceModifier(int daysTillDeparture) {
		this.daysTillDeparture = daysTillDeparture;
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
