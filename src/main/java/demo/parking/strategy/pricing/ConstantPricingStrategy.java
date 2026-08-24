package demo.parking.strategy.pricing;

import demo.parking.entities.Ticket;

import java.math.BigDecimal;

public class ConstantPricingStrategy implements PricingStrategy {
    @Override
    public BigDecimal calculate(Ticket ticket) {
        return null;
    }
}
