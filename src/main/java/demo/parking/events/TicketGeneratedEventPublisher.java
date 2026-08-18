package demo.parking.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TicketGeneratedEventPublisher {
    Logger log = LoggerFactory.getLogger(TicketGeneratedEventPublisher.class);
    private final ApplicationEventPublisher publisher;

    public TicketGeneratedEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishCustomTicketGeneratedPublisher(UUID vehicleId, UUID ticketId, Long deviceId) {
        log.warn("TicketGeneratedEventPublisher Publishing custom event");
        TicketGeneratedEvent ticketGeneratedEvent = new TicketGeneratedEvent(this, vehicleId, ticketId, deviceId);
        publisher.publishEvent(ticketGeneratedEvent);
    }
}
