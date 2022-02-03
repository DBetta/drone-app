package com.musalasoft.drone.drone;

/**
 * @author Denis Gitonga
 */
public class DroneNotFoundException extends RuntimeException {
    public DroneNotFoundException(String message) {
        super(message);
    }
}
