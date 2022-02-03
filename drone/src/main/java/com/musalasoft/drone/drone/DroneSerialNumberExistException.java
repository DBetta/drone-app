package com.musalasoft.drone.drone;

/**
 * @author Denis Gitonga
 */
public class DroneSerialNumberExistException extends RuntimeException {
    public DroneSerialNumberExistException(String message) {
        super(message);
    }
}
