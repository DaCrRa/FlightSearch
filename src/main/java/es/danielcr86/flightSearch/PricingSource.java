package es.danielcr86.flightSearch;

import java.math.BigDecimal;

public interface PricingSource {

	BigDecimal getPrice(String flightCode);

}
