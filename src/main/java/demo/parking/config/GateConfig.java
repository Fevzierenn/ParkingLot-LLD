package demo.parking.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "parking.gate")
@Getter
@Setter
public class GateConfig {
    private Duration openTimeout = Duration.ofSeconds(10);
}
