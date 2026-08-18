package demo.parking.events;

import lombok.*;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
@Setter
public class TicketGeneratedEvent extends ApplicationEvent {
    private UUID vehicleId;
    private UUID ticketId;
    private Long deviceId;

    public TicketGeneratedEvent(Object source, UUID vehicleId, UUID ticketId, Long deviceId) {
        super(source);
        this.vehicleId = vehicleId;
        this.ticketId = ticketId;
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "TicketGeneratedEvent{" +
                "vehicleId=" + vehicleId +
                ", ticketId=" + ticketId +
                ", DeviceId=" + deviceId+
                '}';
    }
}
