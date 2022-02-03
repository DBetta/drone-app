package com.musalasoft.drone.drone;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static java.lang.String.format;

/**
 * @author Denis Gitonga
 */
@Service
@RequiredArgsConstructor
public class DroneService {
    private final DroneRepository droneRepository;

    @Transactional
    public Mono<DroneDto> createDrone(CreateDroneDto createDroneDto) {

        Mono<Void> serialNumberNotExisting = validateSerialNumberNotExisting(createDroneDto);

        Mono<DroneDto> droneDtoMono = saveDrone(createDroneDto);

        return serialNumberNotExisting.then(droneDtoMono);
    }

    @Transactional(readOnly = true)
    public Flux<DroneDto> fetchAvailableDrones() {
        Specification<DroneEntity> idleDroneSpecification = (root, query, cb) ->
                cb.equal(root.get("state"), DroneState.IDLE);
        return Flux.defer(() -> Flux.fromIterable(droneRepository.findAll(idleDroneSpecification))
                        .subscribeOn(Schedulers.boundedElastic()))
                .map(drone -> DroneDto.builder()
                        .id(drone.getId())
                        .serialNumber(drone.getSerialNumber())
                        .batteryCapacity(drone.getBatteryCapacity())
                        .model(drone.getModel())
                        .state(drone.getState())
                        .weightLimit(drone.getWeightLimit())
                        .createdAt(drone.getCreatedAt())
                        .updatedAt(drone.getUpdatedAt())
                        .build());
    }

    @NotNull
    private Mono<DroneDto> saveDrone(CreateDroneDto createDroneDto) {
        DroneEntity droneEntity = DroneEntity.builder()
                .serialNumber(createDroneDto.getSerialNumber())
                .batteryCapacity(createDroneDto.getBatteryCapacity())
                .model(createDroneDto.getModel())
                .state(createDroneDto.getState())
                .weightLimit(createDroneDto.getWeightLimit())
                .build();
        return Mono.defer(() -> Mono.fromCallable(() -> droneRepository.save(droneEntity))
                        .publishOn(Schedulers.boundedElastic()))
                .map(drone -> DroneDto.builder()
                        .id(drone.getId())
                        .serialNumber(drone.getSerialNumber())
                        .batteryCapacity(drone.getBatteryCapacity())
                        .model(drone.getModel())
                        .state(drone.getState())
                        .weightLimit(drone.getWeightLimit())
                        .createdAt(drone.getCreatedAt())
                        .updatedAt(drone.getUpdatedAt())
                        .build());
    }

    @NotNull
    private Mono<Void> validateSerialNumberNotExisting(CreateDroneDto createDroneDto) {
        Specification<DroneEntity> serialNumberPredicate = (root, query, cb) ->
                cb.equal(cb.lower(root.get("serialNumber")), StringUtils.lowerCase(createDroneDto.getSerialNumber()));

        return Mono.defer(() -> Mono.fromCallable(() -> droneRepository.findAll(serialNumberPredicate))
                        .subscribeOn(Schedulers.boundedElastic()))
                .flatMap(droneEntities -> {
                    if (droneEntities.isEmpty()) return Mono.empty();

                    final var message = format("drone with serial number: %s already exist", createDroneDto.getSerialNumber());
                    return Mono.error(new DroneSerialNumberExistException(message));
                });
    }
}
