package demo.parking.events;

import demo.parking.enums.DeviceStatus;
import demo.parking.services.SpotDeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class TicketGeneratedEventListener implements ApplicationListener<TicketGeneratedEvent> {
    Logger logger = LoggerFactory.getLogger(TicketGeneratedEventListener.class);
    private final SpotDeviceService spotDeviceService;

    public TicketGeneratedEventListener(SpotDeviceService spotDeviceService) {
        this.spotDeviceService = spotDeviceService;
    }

    @Override
    public void onApplicationEvent(TicketGeneratedEvent event) {
        logger.warn("Ticket generated event: " + event);
        spotDeviceService.startWaiting(event.getDeviceId());
    }

}
