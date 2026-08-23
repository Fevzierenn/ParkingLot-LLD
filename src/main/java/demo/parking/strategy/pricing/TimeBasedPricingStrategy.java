package demo.parking.strategy.pricing;

import demo.parking.Exceptions.PriceCanNotCalculatedException;
import demo.parking.config.PricingPropertyConfig;
import demo.parking.entities.Ticket;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class TimeBasedPricingStrategy implements PricingStrategy {
    Logger log = LoggerFactory.getLogger(TimeBasedPricingStrategy.class);

    private final PricingPropertyConfig pricingPropertyConfig;

    @Override
    public BigDecimal calculate(Ticket ticket) {
        if(ticket == null || ticket.getVehicle() == null
                || ticket.getEntryTime() == null
        || ticket.getExitTime() == null || pricingPropertyConfig.getHourlyRate() == null)
            throw new PriceCanNotCalculatedException("One of this -> Ticket/ Ticket entry-exit time/ Vehicle / Hourly rate cannot be null");

        Duration duration = Duration.between(
                ticket.getEntryTime(),
                ticket.getExitTime()
        );
        long minutes = duration.toMinutes();
        long ceilHour = (long) Math.ceil(minutes/60);
        log.info("Minutes: " + minutes );
        log.info("Ceil Hour: " + ceilHour );

        BigDecimal price =pricingPropertyConfig.getHourlyRate().multiply(BigDecimal.valueOf(ceilHour).
                multiply(BigDecimal.valueOf(ticket.getVehicle().getType().getPriceMultiplier())));

        log.info("Price calculated: " + price );
        return price;
    }
}
