package demo.parking.services;

import demo.parking.Exceptions.GateNotAvailableException;
import demo.parking.Exceptions.GateNotFoundException;
import demo.parking.config.GateConfig;
import demo.parking.entities.Gate;
import demo.parking.enums.GateStatus;
import demo.parking.enums.GateType;
import demo.parking.repositories.GateRepository;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
public class GateService {
    private static final Logger logger = LoggerFactory.getLogger(GateService.class);

    private final GateRepository gateRepository;
    private final GateConfig gateConfig;
    private final Clock clock;

    public GateService(GateRepository gateRepository, GateConfig gateConfig, Clock clock) {
        this.gateRepository = gateRepository;
        this.gateConfig = gateConfig;
        this.clock = clock;
    }

    public Gate save(@NotNull Gate gate) {
        return gateRepository.save(gate);
    }

    public Gate getAvailableEntryGate() {
        return gateRepository.findFirstByTypeAndStatus(GateType.ENTRY, GateStatus.CLOSED)
                .orElseThrow(() -> new GateNotAvailableException(
                        "No idle entry gate is currently available."));
    }

    public Gate findGateById(Long gateId) {
        return gateRepository.findById(gateId).orElseThrow(
                () -> new GateNotFoundException("Gate not found with id: " + gateId)
        );
    }

    @Transactional
    public Gate findEntryGateByIdForUpdate(Long gateId) {
        Gate gate = gateRepository.findByIdForUpdate(gateId).orElseThrow(
                () -> new GateNotFoundException("Gate not found with id: " + gateId)
        );
        gate.assertUsableForEntry();
        return gate;
    }

    @Transactional
    public void openForEntry(Gate gate) {
        gate.openForEntry(LocalDateTime.now(clock));
        logger.info("Gate {} opened for entry.", gate.displayName());
    }

    @Transactional
    public void vehiclePassed(Long gateId) {
        Gate gate = findGateById(gateId);
        gate.close();
        logger.info("Gate {} closed after vehicle passed.", gate.displayName());
    }

    @Transactional
    public int closeExpiredOpenGates() {
        LocalDateTime threshold = LocalDateTime.now(clock).minus(gateConfig.getOpenTimeout());

        int closed = gateRepository.closeExpiredOpenGates(
                GateStatus.OPEN, GateStatus.CLOSED, threshold);

        if (closed > 0) {
            logger.warn("{} gate(s) stayed open past {}. Forced them closed.", closed, threshold);
        }
        return closed;
    }
}
