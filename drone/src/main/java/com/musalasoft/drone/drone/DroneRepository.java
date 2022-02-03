package com.musalasoft.drone.drone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Denis Gitonga
 */
public interface DroneRepository extends JpaRepository<DroneEntity, Long>, JpaSpecificationExecutor<DroneEntity> {

    @Modifying
    @Transactional
    @Query("update DroneEntity drone set drone.state = :state where drone.id = :droneId")
    void updateDroneState(@Param("droneId") Long droneId, @Param("state") DroneState state);

}
