package com.musalasoft.drone.drone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Denis Gitonga
 */
public interface DroneRepository extends JpaRepository<DroneEntity, Long>, JpaSpecificationExecutor<DroneEntity> {
}
