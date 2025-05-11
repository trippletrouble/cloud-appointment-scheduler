package de.hof_university.narenprak.cc2025.appointment_scheduler.repository;

import de.hof_university.narenprak.cc2025.appointment_scheduler.model.AppointmentCoordination;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface AppointmentCoordinationRepository extends MongoRepository<AppointmentCoordination, String> {

}
