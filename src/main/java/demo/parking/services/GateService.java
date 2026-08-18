package demo.parking.services;

import demo.parking.entities.Gate;
import demo.parking.enums.GateStatus;
import demo.parking.enums.GateType;
import demo.parking.repositories.GateRepository;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.stereotype.Service;


@Service
public class GateService {
    private final GateRepository gateRepository;
    public GateService(GateRepository gateRepository) {
        this.gateRepository = gateRepository;
    }
    public Gate save(@NotEmpty Gate gate) {
        return gateRepository.save(gate);
    }
    public Gate getAvailableEntryGate(){
        return gateRepository.findByTypeAndStatus(GateType.ENTRY, GateStatus.CLOSED).getFirst();
    }
    public Gate findGateById(Long gateId) {
        return gateRepository.findById(gateId).orElseThrow(
                ()-> new RuntimeException("Gate not found")
        );
    }
}
