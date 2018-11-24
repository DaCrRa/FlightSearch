package es.danielcr86.flightSearch;

import java.math.BigDecimal;

public interface PriceModifier {

	BigDecimal modifyPrice(BigDecimal basePrice);

}
