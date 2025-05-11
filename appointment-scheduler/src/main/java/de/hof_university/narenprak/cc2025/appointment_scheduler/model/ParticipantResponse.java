package de.hof_university.narenprak.cc2025.appointment_scheduler.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Data
@NoArgsConstructor
@Document(collection = "participantResponses")
public class ParticipantResponse {
    @Id
    private String id;
    private String coordinationId;
    private String participantName;
    private Map<Integer, Boolean> availability = new HashMap<>();
    private String comment;
    private LocalDateTime respondedAt;

    public ParticipantResponse(String coordinationId, String participantName) {
        this.coordinationId = coordinationId;
        this.participantName = participantName;
        this.respondedAt = LocalDateTime.now();
    }

        {
         if(this.respondedAt == null) {
        this.respondedAt = LocalDateTime.now();
        } 
    }
}
