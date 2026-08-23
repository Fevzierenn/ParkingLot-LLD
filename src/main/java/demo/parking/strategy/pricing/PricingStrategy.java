package demo.parking.strategy.pricing;

import demo.parking.entities.Ticket;
import demo.parking.entities.Vehicle;

import java.math.BigDecimal;

public interface PricingStrategy {

    BigDecimal calculate(Ticket ticket);
}
