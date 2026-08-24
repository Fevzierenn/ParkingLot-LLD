package demo.parking.entities;

import demo.parking.Exceptions.GateBusyException;
import demo.parking.Exceptions.GateNotAvailableException;
import demo.parking.Exceptions.InvalidGateTypeException;
import demo.parking.enums.GateStatus;
import demo.parking.enums.GateType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Gate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private GateStatus status;
    @Enumerated(EnumType.STRING)
    private GateType type;


    private LocalDateTime openedAt;

    public String displayName() {
        return name != null ? name : type + "#" + id;
    }

    public boolean isOpen() {
        return status == GateStatus.OPEN;
    }

    public boolean isOutOfService() {
        return status == GateStatus.OUT_OF_SERVICE;
    }

    /**
     * A gate can admit a vehicle only when it is an entry gate, in service,
     * and its barrier is currently down.
     */
    public void assertUsableForEntry() {
        if (type != GateType.ENTRY) {
            throw new InvalidGateTypeException(
                    "Gate " + displayName() + " is an " + type
                            + " gate and cannot be used for entry.");
        }
        if (isOutOfService()) {
            throw new GateNotAvailableException(
                    "Gate " + displayName() + " is out of service.");
        }
        if (isOpen()) {
            throw new GateBusyException(
                    "Gate " + displayName() + " is already admitting a vehicle.");
        }
    }

    public void assertUsableForExit() {
        if (type != GateType.EXIT) {
            throw new InvalidGateTypeException(
                    "Gate " + displayName() + " is an " + type
                            + " gate and cannot be used for exit.");
        }
        if (isOutOfService()) {
            throw new GateNotAvailableException(
                    "Gate " + displayName() + " is out of service.");
        }
        if (isOpen()) {
            throw new GateBusyException(
                    "Gate " + displayName() + " is already admitting a vehicle.");
        }
    }

    /** Raises the barrier. Only legal from a CLOSED, in-service entry gate. */
    public void openForEntry(LocalDateTime now) {
        assertUsableForEntry();
        this.status = GateStatus.OPEN;
        this.openedAt = now;
    }

    /** Raises the barrier. Only legal from a CLOSED, in-service entry gate. */
    public void openForExit(LocalDateTime now) {
        assertUsableForExit();
        this.status = GateStatus.OPEN;
        this.openedAt = now;
    }

    /** Lowers the barrier. A gate taken out of service stays that way. */
    public void close() {
        if (isOutOfService()) {
            return;
        }
        this.status = GateStatus.CLOSED;
        this.openedAt = null;
    }

    @Override
    public String toString() {
        return "Gate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", openedAt=" + openedAt +
                '}';
    }
}
