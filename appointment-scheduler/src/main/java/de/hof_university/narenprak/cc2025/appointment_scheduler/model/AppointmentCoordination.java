package de.hof_university.narenprak.cc2025.appointment_scheduler.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "appointmentCoordinations")
public class AppointmentCoordination {

    @Id
    private String id;
    private String title;
    private String description;
    private String proposerName;
    private List<TimeSuggestion> suggestions;
    private LocalDateTime createdAt;


    public AppointmentCoordination() {
        this.suggestions = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
    }


    public AppointmentCoordination(String title, String description, String proposerName) {
        this.title = title;
        this.description = description;
        this.proposerName = proposerName;
        this.suggestions = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
    }

    public AppointmentCoordination(String id, String title, String description, String proposerName, List<TimeSuggestion> suggestions, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.proposerName = proposerName;
        this.suggestions = (suggestions != null) ? suggestions : new ArrayList<>();
        this.createdAt = (createdAt != null) ? createdAt : LocalDateTime.now();
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProposerName() {
        return proposerName;
    }

    public void setProposerName(String proposerName) {
        this.proposerName = proposerName;
    }

    public List<TimeSuggestion> getSuggestions() {
        if (this.suggestions == null) {
            this.suggestions = new ArrayList<>();
        }
        return suggestions;
    }

    public void setSuggestions(List<TimeSuggestion> suggestions) {
        this.suggestions = suggestions;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void addSuggestion(TimeSuggestion suggestion) {
        if (this.suggestions == null) {
            this.suggestions = new ArrayList<>();
        }
        if (this.suggestions.size() < 3) {
            this.suggestions.add(suggestion);
        } else {
            System.err.println("Cannot add more than 3 suggestions to AppointmentCoordination.");
        }
    }

}
