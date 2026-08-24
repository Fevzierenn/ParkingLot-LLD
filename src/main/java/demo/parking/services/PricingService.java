package demo.parking.services;


import demo.parking.ParkingLotSpringApplication;
import demo.parking.config.PricingPropertyConfig;
import demo.parking.entities.Ticket;
import demo.parking.enums.PricingPolicy;
import demo.parking.strategy.pricing.PricingStrategy;
import demo.parking.strategy.pricing.PricingStrategyFactory;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Getter
@Setter
@Component
public class PricingService {
    private static final Logger logger = LoggerFactory.getLogger(PricingService.class);
    private final PricingPropertyConfig pricingPolicyConfig;
    private final PricingStrategyFactory pricingStrategyFactory;

    public PricingService(PricingPropertyConfig pricingPolicyConfig, PricingStrategyFactory pricingStrategyFactory) {
        this.pricingPolicyConfig = pricingPolicyConfig;
        this.pricingStrategyFactory = pricingStrategyFactory;
    }
    public PricingPolicy getPricingPolicy() {
        logger.info("getPricingPolicy: " + pricingPolicyConfig.getPolicy());
        return pricingPolicyConfig.getPolicy();
    }
    public String getPricingDescription(){
        logger.info("getPricingDescription: " + pricingPolicyConfig.getDescription());
        return pricingPolicyConfig.getDescription();
    }

    public BigDecimal calculate(Ticket ticket) {
        logger.info("PricingStrategyService -> calculate method works.");
        if(ticket == null || ticket.getPricingPolicy() == null) throw new RuntimeException("Ticket is null or pricingPolicy did not included.");

        PricingStrategy policy = pricingStrategyFactory.getPricingStrategy(ticket.getPricingPolicy());
        logger.info("Policy is -> {}.", policy);
        return policy.calculate(ticket);
    }

}
