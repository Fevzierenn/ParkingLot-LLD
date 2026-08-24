package demo.parking.strategy.pricing;

import demo.parking.entities.Ticket;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class WeekendPricingStrategy implements PricingStrategy {
    @Override
    public BigDecimal calculate(Ticket ticket) {
        return null;
    }
}
