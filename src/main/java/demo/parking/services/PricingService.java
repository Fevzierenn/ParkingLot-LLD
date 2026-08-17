package demo.parking.services;


import demo.parking.ParkingLotSpringApplication;
import demo.parking.config.PricingPropertyConfig;
import demo.parking.enums.PricingPolicy;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class PricingService {
    Logger logger = LoggerFactory.getLogger(ParkingLotSpringApplication.class);
    private final PricingPropertyConfig pricingPolicyConfig;
    public PricingService(PricingPropertyConfig pricingPolicyConfig) {
        this.pricingPolicyConfig = pricingPolicyConfig;
    }
    public PricingPolicy getPricingPolicy() {
        logger.info("getPricingPolicy: " + pricingPolicyConfig.getPolicy());
        return pricingPolicyConfig.getPolicy();
    }
    public String getPricingDescription(){
        logger.info("getPricingDescription: " + pricingPolicyConfig.getDescription());
        return pricingPolicyConfig.getDescription();
    }

}
