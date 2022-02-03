package com.musalasoft.drone.medication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Denis Gitonga
 */
public interface MedicationEntityRepository extends JpaRepository<MedicationEntity, Long>, JpaSpecificationExecutor<MedicationEntity> {
}
