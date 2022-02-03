package com.musalasoft.drone.medication;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Denis Gitonga
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/drone/medication")
public class MedicationController {
    private final MedicationService medicationService;

    @PutMapping("/{droneId}")
    public Mono<MedicationDto> loadMedication(@Validated @RequestBody MedicationRequestDto medicationRequestDto,
                                              @PathVariable Long droneId) {
        return medicationService.loadMedication(droneId, medicationRequestDto);
    }

    @GetMapping("/{droneId}")
    public Flux<MedicationDto> getDroneLoadedMedications(@PathVariable Long droneId) {
        return medicationService.getDroneMedicationsLoaded(droneId);
    }
}
