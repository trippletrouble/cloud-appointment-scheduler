package de.hof_university.narenprak.cc2025.appointment_scheduler.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "appointmentCoordinations")
public class AppointmentCoordination {

    @Id
    private String id;
    private String title;
    private String description;
    private String proposerName;
    private List<TimeSuggestion> suggestions = new ArrayList<>();
    private LocalDateTime createdAt;

    public AppointmentCoordination(String title, String description, String proposerName) {
        this.title = title;
        this.description = description;
        this.proposerName = proposerName;
        this.createdAt = LocalDateTime.now();
    }

    {
        if(this.createdAt == null) {
        this.createdAt = LocalDateTime.now();
    }
    }
    public void addSuggestion(TimeSuggestion suggestion) {
        if (this.suggestions.size() < 3) {
            this.suggestions.add(suggestion);
        } else {
            System.err.println("Cannot add more than 3 suggestions.");
        }
    }
}

