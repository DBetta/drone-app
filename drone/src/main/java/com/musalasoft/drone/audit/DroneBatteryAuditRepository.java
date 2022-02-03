package com.musalasoft.drone.audit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Denis Gitonga
 */
public interface DroneBatteryAuditRepository extends JpaRepository<DroneBatteryAuditEntity, Long>, JpaSpecificationExecutor<DroneBatteryAuditEntity> {
}
