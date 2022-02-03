package com.musalasoft.drone;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author Denis Gitonga
 */
@Validated
@ConfigurationProperties(prefix = "drone")
public class DroneConfiguration {
    private Battery battery;

    public Battery getBattery() {
        return battery;
    }

    public void setBattery(Battery battery) {
        this.battery = battery;
    }

    @Validated
    public static class Battery {
        @Min(0)
        @Max(100)
        private int minimalCapacity;

        public int getMinimalCapacity() {
            return minimalCapacity;
        }

        public void setMinimalCapacity(int minimalCapacity) {
            this.minimalCapacity = minimalCapacity;
        }
    }
}
