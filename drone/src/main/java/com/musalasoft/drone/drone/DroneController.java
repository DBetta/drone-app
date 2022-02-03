package com.musalasoft.drone.drone;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Denis Gitonga
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/drone")
public class DroneController {

    private final DroneService droneService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<DroneDto> createDrone(@Validated @RequestBody CreateDroneDto createDroneDto) {
        return droneService.createDrone(createDroneDto);
    }

    @GetMapping("available")
    public Flux<DroneDto> getAvailableDrones() {
        return droneService.fetchAvailableDrones();
    }

    @GetMapping("/{droneId}/battery-capacity")
    public Mono<DroneBatteryCapacityDto> getBatteryCapacity(@PathVariable Long droneId) {
        return droneService.getDroneBatteryCapacity(droneId);
    }

}
