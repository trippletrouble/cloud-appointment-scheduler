package de.hof_university.narenprak.cc2025.appointment_scheduler.repository;
import de.hof_university.narenprak.cc2025.appointment_scheduler.model.ParticipantResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
public interface ParticipantResponseRepository extends MongoRepository<ParticipantResponse, String> {
    List<ParticipantResponse> findByCoordinationId(String coordinationId);
}
