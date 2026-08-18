package demo.parking.scheduler;

import demo.parking.services.SpotDeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpotDeviceWaitingScheduler {
    private final SpotDeviceService spotDeviceService;

    @Scheduled(fixedRate = 30000)  //every 30 second check waiting time.
    public void checkWaitingDevices() {
        spotDeviceService.clearExpiredWaitingDevices();
    }
}
