package com.musalasoft.drone.medication;

import com.musalasoft.drone.drone.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

/**
 * @author Denis Gitonga
 */
@Service
@Transactional
@RequiredArgsConstructor
public class MedicationService {
    private final MedicationEntityRepository medicationEntityRepository;
    private final DroneRepository droneRepository;

    @Transactional
    public Mono<MedicationDto> loadMedication(long droneId, MedicationRequestDto medicationRequestDto) {
        // validate droneId exist
        Mono<DroneEntity> droneEntityMono = getDrone(droneId);

        // load drone medications
        Mono<List<MedicationEntity>> droneMedicationsMono = getDroneMedications(droneId);

        Mono<DroneDto> validateDroneLoadingMono = droneEntityMono.zipWith(droneMedicationsMono)
                .flatMap(tuple -> {
                    try {
                        DroneEntity drone = tuple.getT1();

                        DroneDto droneDto = DroneDto.builder()
                                .id(drone.getId())
                                .serialNumber(drone.getSerialNumber())
                                .batteryCapacity(drone.getBatteryCapacity())
                                .model(drone.getModel())
                                .state(drone.getState())
                                .weightLimit(drone.getWeightLimit())
                                .createdAt(drone.getCreatedAt())
                                .updatedAt(drone.getUpdatedAt())
                                .build();
                        validateDroneState(droneDto);
                        validateDroneWeightLimit(droneDto, tuple.getT2(), medicationRequestDto);
                        validateDroneBatteryCapacity(droneDto);

                        return Mono.just(droneDto);
                    } catch (Throwable throwable) {
                        return Mono.error(throwable);
                    }
                });

        return validateDroneLoadingMono
                .flatMap(drone -> saveMedication(drone, medicationRequestDto))
                .flatMap(medicationDto -> updateDroneStateToLoading(droneId)
                        .then(Mono.justOrEmpty(medicationDto)));
    }

    private Mono<Void> updateDroneStateToLoading(final long droneId) {
        return Mono.defer(() -> Mono.fromCallable(() -> {
                            droneRepository.updateDroneState(droneId, DroneState.LOADING);

                            return Optional.empty();
                        })
                        .publishOn(Schedulers.boundedElastic()))
                .flatMap(Mono::justOrEmpty)
                .then();
    }

    private Mono<MedicationDto> saveMedication(DroneDto drone, MedicationRequestDto medicationRequestDto) {
        MedicationEntity medicationEntity = MedicationEntity.builder()
                .droneId(drone.getId())
                .code(medicationRequestDto.getCode())
                .image(medicationRequestDto.getImage())
                .weight(medicationRequestDto.getWeight())
                .name(medicationRequestDto.getName())
                .build();


        return Mono.defer(() -> Mono.fromCallable(() -> medicationEntityRepository.save(medicationEntity))
                        .publishOn(Schedulers.boundedElastic()))
                .map(medication -> MedicationDto.builder()
                        .id(medication.getId())
                        .drone(drone)
                        .code(medication.getCode())
                        .image(medication.getImage())
                        .weight(medication.getWeight())
                        .name(medication.getName())
                        .createdAt(medication.getCreatedAt())
                        .updatedAt(medication.getUpdatedAt())
                        .build());
    }

    private void validateDroneBatteryCapacity(DroneDto drone) {
        if (drone.getBatteryCapacity() < 25) {
            var message = format("Drone battery capacity is below: %d", 25);
            throw new DroneBatteryCapacityLowException(message);
        }
    }

    private void validateDroneWeightLimit(DroneDto drone, List<MedicationEntity> medications, MedicationRequestDto medicationRequestDto) {
        final int medicationsLoadedThusFar = medications.stream()
                .mapToInt(MedicationEntity::getWeight)
                .sum();
        final int totalMedicationWeight = medicationsLoadedThusFar + medicationRequestDto.getWeight();
        if (totalMedicationWeight > drone.getWeightLimit()) {
            var difference = totalMedicationWeight - drone.getWeightLimit();
            var message = format("Drone carrying capacity exceeded by: %d", difference);
            throw new DroneCarryingCapacityExceededException(message);
        }

    }

    private void validateDroneState(DroneDto droneDto) {
        DroneState state = droneDto.getState();
        if (state == DroneState.IDLE || state == DroneState.LOADING) return;

        var message = "Invalid drone loading state";
        throw new DroneInvalidLoadStateException(message);
    }

    private Mono<List<MedicationEntity>> getDroneMedications(long droneId) {
        Specification<MedicationEntity> droneIdSpecification = (root, query, cb) ->
                cb.equal(root.get("droneId"), droneId);
        return Mono.defer(() -> Mono.fromCallable(() -> medicationEntityRepository.findAll(droneIdSpecification))
                .subscribeOn(Schedulers.boundedElastic()));
    }

    private Mono<DroneEntity> getDrone(long droneId) {
        return Mono.defer(() -> Mono.fromCallable(() -> droneRepository.findById(droneId))
                        .subscribeOn(Schedulers.boundedElastic())
                        .flatMap(Mono::justOrEmpty))
                .switchIfEmpty(Mono.error(new RuntimeException()))
                .onErrorMap(throwable -> {
                    var message = format("could not get drone with the id: %d", droneId);
                    return new DroneNotFoundException(message);
                });
    }
}
