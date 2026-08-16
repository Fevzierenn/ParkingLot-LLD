package demo.parking.config;

import demo.parking.enums.PricingPolicy;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "parking.pricing")
@Getter
@Setter
public class PricingPropertyConfig {
        private PricingPolicy policy;
        private String description;

}
