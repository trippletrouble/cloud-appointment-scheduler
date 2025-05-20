package de.hof_university.narenprak.cc2025.appointment_scheduler.model;

import java.time.LocalDateTime;

public class TimeSuggestion {
    private LocalDateTime dateTime;
    private String description;

    public TimeSuggestion() {
    }

    public TimeSuggestion(LocalDateTime dateTime, String description) {
        this.dateTime = dateTime;
        this.description = description;
    }

    // Getters and Setters

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
