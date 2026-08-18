package demo.parking.services;

import demo.parking.Exceptions.SpotDeviceNotFoundException;
import demo.parking.Exceptions.SpotDeviceNotWorkingException;
import demo.parking.config.SpotDeviceConfig;
import demo.parking.entities.SpotDevice;
import demo.parking.enums.DeviceStatus;
import demo.parking.enums.SpotStatus;
import demo.parking.repositories.SpotDeviceRepository;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SpotDeviceService {
    Logger logger = LoggerFactory.getLogger(SpotDeviceService.class);

    private final SpotDeviceRepository spotDeviceRepository;
    private final SpotDeviceConfig spotDeviceConfig;
    private final EntityManager entityManager;

    public SpotDeviceService(SpotDeviceRepository spotDeviceRepository, SpotDeviceConfig spotDeviceConfig, EntityManager entityManager) {
        this.spotDeviceRepository = spotDeviceRepository;
        this.spotDeviceConfig = spotDeviceConfig;
        this.entityManager = entityManager;
    }

    public SpotDevice findSpotDeviceById(Long deviceId) {
        return spotDeviceRepository.findById(deviceId).
                orElseThrow(()-> new SpotDeviceNotFoundException("SpotDevice not found with id: " + deviceId));
    }


    public SpotDevice startWaiting(Long spotDeviceId) {
        logger.info("Device Id: " + spotDeviceId);
        SpotDevice device = spotDeviceRepository.findById(spotDeviceId).orElseThrow(()-> new SpotDeviceNotFoundException("Device Id: " + spotDeviceId + " not found"));
        logger.info("Device status: " + device.getDeviceStatus());

        if (device.getDeviceStatus() == DeviceStatus.OFFLINE) throw new SpotDeviceNotWorkingException("Device Id: " + spotDeviceId + " is offline and currently not working properly.");
        device.setDeviceStatus(DeviceStatus.WAITING);
        logger.info("Device config waiting minute: " + spotDeviceConfig.getWaitingTime());
        device.setWaitingStartedAt(LocalDateTime.now());
        device.setWaitingDeadline(device.getWaitingStartedAt().plus(spotDeviceConfig.getWaitingTime()));
        logger.info("Device Waiting start: " + device.getWaitingStartedAt());
        logger.info("Device Waiting deadline: " + device.getWaitingDeadline());
        return device;
    }

    @Transactional
    public void clearExpiredWaitingDevices() {

        LocalDateTime now = LocalDateTime.now();

        List<SpotDevice> expiredDevices =
                spotDeviceRepository.findAllByDeviceStatus(DeviceStatus.WAITING)
                        .stream()
                        .filter(device ->
                                device.getWaitingDeadline() != null &&
                                        device.getWaitingStartedAt() != null)
                        .filter(device ->
                                device.getWaitingDeadline().isBefore(now))
                        .toList();

        expiredDevices.forEach(device -> {
            logger.info(
                    "Before update -> DeviceId={}, version={}, status={}",
                    device.getId(),
                    device.getVersion(),
                    device.getDeviceStatus()
            );

            device.setDeviceStatus(DeviceStatus.EMPTY);
            device.setWaitingStartedAt(null);
            device.setWaitingDeadline(null);
            device.getSpot().setStatus(SpotStatus.AVAILABLE);
            logger.info(
                    "Changed -> DeviceId={}, status={}, version={}",
                    device.getId(),
                    device.getDeviceStatus(),
                    device.getVersion()
            );
        });

        spotDeviceRepository.saveAll(expiredDevices);

        entityManager.flush();

        expiredDevices.forEach(device -> {
            logger.info(
                    "After flush -> DeviceId={}, version={}, status={}",
                    device.getId(),
                    device.getVersion(),
                    device.getDeviceStatus()
            );
        });
    }

    @Transactional
    public void resetDevice(SpotDevice device) {
        logger.info("Device Id Released to EMPTY Mode: " + device.getId());
        device.setDeviceStatus(DeviceStatus.EMPTY);
        device.setVehiclePlate(null);
        device.setWaitingStartedAt(null);
        device.setWaitingDeadline(null);
        device.setMessage("Ready");
    }

    @Transactional
    public void occupy(SpotDevice device, String plateNo) {
        device.getSpot().setStatus(SpotStatus.OCCUPIED);
        device.setVehiclePlate(plateNo);
        device.setDeviceStatus(DeviceStatus.OCCUPIED);
        device.setWaitingStartedAt(null);
        device.setWaitingDeadline(null);
        device.setMessage("Vehicle Detected. Now OCCUPIED");
    }


}
