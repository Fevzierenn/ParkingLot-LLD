package demo.parking.strategy.pricing;

import demo.parking.Exceptions.PriceCanNotCalculatedException;
import demo.parking.config.PricingPropertyConfig;
import demo.parking.entities.Ticket;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class TimeBasedPricingStrategy implements PricingStrategy {
    private static final Logger log = LoggerFactory.getLogger(TimeBasedPricingStrategy.class);

    private static final long GRACE_PERIOD_MINUTES = 15;
    private static final long MINUTES_PER_HALF_HOUR = 30;
    private static final BigDecimal HALF_HOUR_IN_HOURS = new BigDecimal("0.5");
    private static final BigDecimal PENALTY_MULTIPLIER = BigDecimal.valueOf(2);
    private static final int CURRENCY_SCALE = 2;

    private final PricingPropertyConfig pricingPropertyConfig;

    @Override
    public BigDecimal calculate(Ticket ticket) {
        validateTicket(ticket);
        BigDecimal billableHours = calculateVehicleStay(ticket);

        BigDecimal basePrice = calculateRegularPrice(ticket, billableHours);
        BigDecimal finalPrice = applyPenaltyIfNecessary(ticket, basePrice);

        finalPrice = finalPrice.setScale(CURRENCY_SCALE, RoundingMode.HALF_UP);
        log.info("Price calculated: {}", finalPrice);
        return finalPrice;
    }

    private BigDecimal applyPenaltyIfNecessary(Ticket ticket, BigDecimal basePrice) {
        if (ticket.isPenaltyApplied()) {
            BigDecimal penaltyPrice = basePrice.multiply(PENALTY_MULTIPLIER);
            log.info("Penalty applied. Price doubled based on regular price x 2: {}", penaltyPrice);
            return penaltyPrice;
        }

        log.debug("No penalty applied. Price based on standard price: {}", basePrice);
        return basePrice;
    }

    private void validateTicket(Ticket ticket) {
        if (ticket == null
                || ticket.getVehicle() == null
                || ticket.getEntryTime() == null
                || ticket.getExitTime() == null
                || ticket.getActualSpot() == null
                || ticket.getActualSpot().getAllowedType() == null
                || pricingPropertyConfig.getHourlyRate() == null) {
            throw new PriceCanNotCalculatedException(
                    "One of these -> Ticket / entry-exit time / Vehicle / actual spot / spot type / Hourly rate cannot be null");
        }

        if (ticket.getExitTime().isBefore(ticket.getEntryTime())) {
            throw new PriceCanNotCalculatedException("Exit time cannot be before entry time");
        }
    }

    /**
     * Bills the stay by rounding up to the next half hour, after a grace period.
     * Examples: <15 min -> 0h, 15-30 min -> 0.5h, 31-60 min -> 1h, 61-90 min -> 1.5h, 120 min -> 2h.
     */
    private BigDecimal calculateVehicleStay(Ticket ticket) {
        Duration duration = Duration.between(ticket.getEntryTime(), ticket.getExitTime());
        long minutes = duration.toMinutes();

        BigDecimal billableHours;
        if (minutes < GRACE_PERIOD_MINUTES) {
            billableHours = BigDecimal.ZERO;
        } else {
            long halfHourUnits = ceilDiv(minutes, MINUTES_PER_HALF_HOUR);
            billableHours = BigDecimal.valueOf(halfHourUnits).multiply(HALF_HOUR_IN_HOURS);
        }

        log.debug("Minutes: {}, billable hours: {}", minutes, billableHours);
        return billableHours;
    }

    private BigDecimal calculateRegularPrice(Ticket ticket, BigDecimal hours) {
        BigDecimal spotMultiplier = BigDecimal.valueOf(
                ticket.getActualSpot().getAllowedType().getPriceMultiplier()); // based on actual spot type

        return pricingPropertyConfig.getHourlyRate()
                .multiply(hours)
                .multiply(spotMultiplier);
    }

    private static long ceilDiv(long value, long divisor) {
        return (value + divisor - 1) / divisor;
    }
}
