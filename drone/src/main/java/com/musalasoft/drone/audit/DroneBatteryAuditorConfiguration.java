package com.musalasoft.drone.audit;

import com.musalasoft.drone.drone.DroneEntity;
import com.musalasoft.drone.drone.DroneRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * @author Denis Gitonga
 */
@RequiredArgsConstructor
@Configuration
public class DroneBatteryAuditorConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(DroneBatteryAuditorConfiguration.class);
    private final DroneBatteryAuditRepository droneBatteryAuditRepository;
    private final DroneRepository droneRepository;

    @Scheduled(initialDelay = 60_000, fixedDelay = 60_000)
    public void auditDroneBattery() {
        logger.debug("Auditing drone");

        try {
            List<DroneEntity> drones = droneRepository.findAll();

            List<DroneBatteryAuditEntity> droneBatteryAuditEntities = drones.stream()
                    .map(droneEntity -> DroneBatteryAuditEntity.builder()
                            .batteryCapacity(droneEntity.getBatteryCapacity())
                            .droneId(droneEntity.getId())
                            .build())
                    .toList();
            droneBatteryAuditRepository.saveAll(droneBatteryAuditEntities);
            logger.debug("Done auditing");
        } catch (Exception e) {
            logger.error("Error occurred auditing", e);
        }
    }
}
