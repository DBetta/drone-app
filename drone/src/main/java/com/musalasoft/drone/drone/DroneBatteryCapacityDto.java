package com.musalasoft.drone.drone;

import lombok.Builder;
import lombok.Data;

/**
 * @author Denis Gitonga
 */
@Data
@Builder
public class DroneBatteryCapacityDto {
    private long id;

    private String serialNumber;

    private int batteryCapacity;
}
