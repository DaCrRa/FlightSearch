package es.danielcr86.flightSearch.priceModifiers;

import java.math.BigDecimal;

public interface PriceModifier {

	BigDecimal modifyPrice(BigDecimal basePrice);

}
