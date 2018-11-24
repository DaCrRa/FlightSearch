package es.danielcr86.flightSearch;

import java.math.BigDecimal;

public class DepartureDateBasedPriceModifier implements PriceModifier {

	private int daysTillDeparture;

	public DepartureDateBasedPriceModifier(int daysTillDeparture) {
		this.daysTillDeparture = daysTillDeparture;
	}

	@Override
	public BigDecimal modifyPrice(BigDecimal basePrice) {
		// TODO Auto-generated method stub
		return basePrice;
	}

}
