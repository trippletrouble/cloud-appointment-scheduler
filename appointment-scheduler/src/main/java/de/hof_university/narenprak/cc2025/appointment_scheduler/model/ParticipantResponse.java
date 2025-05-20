package de.hof_university.narenprak.cc2025.appointment_scheduler.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Document(collection = "participantResponses")
public class ParticipantResponse {
    @Id
    private String id;
    private String coordinationId;
    private String participantName;
    private Map<Integer, Boolean> availability;
    private String comment;
    private LocalDateTime respondedAt;

    public ParticipantResponse() {
        this.availability = new HashMap<>();
        this.respondedAt = LocalDateTime.now();
    }


    public ParticipantResponse(String coordinationId, String participantName) {
        this.coordinationId = coordinationId;
        this.participantName = participantName;
        this.availability = new HashMap<>();
        this.respondedAt = LocalDateTime.now();
    }

    public ParticipantResponse(String id, String coordinationId, String participantName, Map<Integer, Boolean> availability, String comment, LocalDateTime respondedAt) {
        this.id = id;
        this.coordinationId = coordinationId;
        this.participantName = participantName;
        this.availability = (availability != null) ? availability : new HashMap<>();
        this.comment = comment;
        this.respondedAt = (respondedAt != null) ? respondedAt : LocalDateTime.now();
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoordinationId() {
        return coordinationId;
    }

    public void setCoordinationId(String coordinationId) {
        this.coordinationId = coordinationId;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public Map<Integer, Boolean> getAvailability() {
        if (this.availability == null) {
            this.availability = new HashMap<>();
        }
        return availability;
    }

    public void setAvailability(Map<Integer, Boolean> availability) {
        this.availability = availability;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getRespondedAt() {
        return respondedAt;
    }

    public void setRespondedAt(LocalDateTime respondedAt) {
        this.respondedAt = respondedAt;
    }

}
