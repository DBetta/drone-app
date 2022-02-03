package com.musalasoft.drone.drone;

/**
 * @author Denis Gitonga
 */
public class DroneBatteryCapacityLowException extends RuntimeException {
    public DroneBatteryCapacityLowException(String message) {
        super(message);
    }
}
