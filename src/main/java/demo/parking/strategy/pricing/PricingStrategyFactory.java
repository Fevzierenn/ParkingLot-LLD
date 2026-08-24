package demo.parking.strategy.pricing;

import demo.parking.enums.PricingPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class PricingStrategyFactory {
    private static final Logger log = LoggerFactory.getLogger(PricingStrategyFactory.class);
    private final ApplicationContext context;

    public PricingStrategyFactory(ApplicationContext context) {
        this.context = context;
    }

    public PricingStrategy getPricingStrategy(PricingPolicy pricingPolicy) {
        log.debug("Resolving pricing strategy for policy: {}", pricingPolicy);
        return switch (pricingPolicy) {
            case TIME_BASED -> context.getBean(TimeBasedPricingStrategy.class);
            case EVENT_BASED -> context.getBean(EventBasedPricingStrategy.class);
            case WEEKEND -> context.getBean(WeekendPricingStrategy.class);
            case FLATRATE_BASED -> context.getBean(ConstantPricingStrategy.class);
            default -> throw new IllegalStateException("Unexpected value: " + pricingPolicy);
        };
    }

}
