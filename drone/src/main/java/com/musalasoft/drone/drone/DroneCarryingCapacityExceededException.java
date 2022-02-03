package com.musalasoft.drone.drone;

/**
 * @author Denis Gitonga
 */
public class DroneCarryingCapacityExceededException extends RuntimeException {
    public DroneCarryingCapacityExceededException(String message) {
        super(message);
    }
}
