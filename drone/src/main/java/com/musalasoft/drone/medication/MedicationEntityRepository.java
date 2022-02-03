package com.musalasoft.drone.medication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Denis Gitonga
 */
public interface MedicationEntityRepository extends JpaRepository<MedicationEntity, Long>, JpaSpecificationExecutor<MedicationEntity> {

    @Query("SELECT sum(medication.weight) from MedicationEntity medication where medication.droneId = :droneId")
    int getDroneLoadedCapacity(@Param("droneId") long droneId);
}
