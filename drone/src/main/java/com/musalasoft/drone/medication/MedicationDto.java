package com.musalasoft.drone.medication;

import com.musalasoft.drone.drone.DroneDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Denis Gitonga
 */
@Data
@Builder
public class MedicationDto {

    private Long id;

    private String name;

    private int weight;

    private String code;

    private String image;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
