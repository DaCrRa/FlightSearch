package es.danielcr86.flightSearch;

import java.math.BigDecimal;
import java.util.Optional;

public interface PricingSource {

	Optional<BigDecimal> getPrice(String flightCode);

}
