package demo.parking.Controller;

import demo.parking.services.GateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gates")
public class GateController {
    Logger logger = LoggerFactory.getLogger(GateController.class);
    private final GateService gateService;

    public GateController(GateService gateService) {
        this.gateService = gateService;
    }

    /** Pass-through sensor: the vehicle has cleared the barrier, so it can come back down. */
    @PostMapping("/{gateId}/vehicle-passed")
    public ResponseEntity<Void> vehiclePassed(@PathVariable Long gateId) {
        logger.info("GATE CONTROLLER: Vehicle passed through gate {}", gateId);
        gateService.vehiclePassed(gateId);
        return ResponseEntity.noContent().build();
    }
}
