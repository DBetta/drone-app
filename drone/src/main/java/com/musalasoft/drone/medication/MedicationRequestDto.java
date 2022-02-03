package com.musalasoft.drone.medication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Denis Gitonga
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicationRequestDto {
    private String name;

    private int weight;

    private String code;

    private String image;
}
