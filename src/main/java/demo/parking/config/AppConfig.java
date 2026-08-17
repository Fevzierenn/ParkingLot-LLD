package demo.parking.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableConfigurationProperties({PricingPropertyConfig.class, SpotDeviceConfig.class})
@ConfigurationPropertiesScan
@EnableScheduling
public class AppConfig {
}
