package com.musalasoft.drone.drone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author Denis Gitonga
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDroneDto {

    @NotEmpty(message = "serial number should not be null")
    @Size(min = 1, max = 100, message = "serial number should be between 1 and 100")
    private String serialNumber;

    private DroneModel model;

    @Min(value = 1, message = "weight limit should not be below 1")
    @Max(value = 500, message = "weight limit should not be above 500")
    private int weightLimit;

    @Min(value = 0, message = "battery capacity should not be below 0%")
    @Max(value = 100, message = "battery capacity should not be above 100%")
    private int batteryCapacity;

    private DroneState state;
}
