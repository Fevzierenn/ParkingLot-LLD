package demo.parking.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;

@ConfigurationProperties(prefix = "parking.device")
@Getter
@Setter

public class SpotDeviceConfig {
    private Duration waitingTime;
}
