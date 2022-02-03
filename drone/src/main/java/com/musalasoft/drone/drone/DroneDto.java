package com.musalasoft.drone.drone;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Denis Gitonga
 */
@Data
@Builder
public class DroneDto {
    private long id;

    private String serialNumber;

    private DroneModel model;

    private int weightLimit;

    private int batteryCapacity;

    private DroneState state;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
