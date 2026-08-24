package demo.parking.strategy.pricing;

import demo.parking.entities.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PricingStrategyService {
    private static final Logger log = LoggerFactory.getLogger(PricingStrategyService.class);
    private final PricingStrategyFactory pricingStrategyFactory;

    public PricingStrategyService(PricingStrategyFactory pricingStrategyFactory) {
        this.pricingStrategyFactory = pricingStrategyFactory;
    }


}
