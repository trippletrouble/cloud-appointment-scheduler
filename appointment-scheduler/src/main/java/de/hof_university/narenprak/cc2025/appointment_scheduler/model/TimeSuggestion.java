package de.hof_university.narenprak.cc2025.appointment_scheduler.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSuggestion {
    private LocalDateTime dateTime;
    private String description;
}



