package demo.parking.scheduler;

import demo.parking.services.GateService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GateAutoCloseScheduler {
    private final GateService gateService;

    @Scheduled(fixedRate = 3000)  // every 3 seconds, close barriers left open
    public void closeExpiredOpenGates() {
        gateService.closeExpiredOpenGates();
    }
}
