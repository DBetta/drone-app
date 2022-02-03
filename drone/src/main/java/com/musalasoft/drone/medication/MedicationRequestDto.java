package com.musalasoft.drone.medication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author Denis Gitonga
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicationRequestDto {
    @NotBlank(message = "name cannot be null/empty")
    @Pattern(regexp = "^[a-zA-Z0-9_\\-]+$", message = "Name can only contain letters, numbers, '-', '_'")
    private String name;

    @Min(0)
    @Max(500)
    private int weight;


    @NotBlank(message = "code cannot be null/empty")
    @Pattern(regexp = "^[A-Z0-9_]+$", message = "Name can only contain letters, numbers, '-', '_'")
    private String code;

    private String image;
}
