package de.hof_university.narenprak.cc2025.appointment_scheduler.controller;
import de.hof_university.narenprak.cc2025.appointment_scheduler.model.*;
import de.hof_university.narenprak.cc2025.appointment_scheduler.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AppointmentController {
    @Autowired
    private AppointmentCoordinationRepository appointmentCoordinationRepository;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("appointmentCoordination", new AppointmentCoordination());
        return "create-appointment";
    }

        @PostMapping("/create")
        public String createAppointment(@ModelAttribute AppointmentCoordination appointmentCoordination,
                @RequestParam(value = "suggestionDateTime1", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dt1,
                @RequestParam(value = "suggestionDescription1", required = false) String desc1,
                @RequestParam(value = "suggestionDateTime2", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dt2,
                @RequestParam(value = "suggestionDescription2", required = false) String desc2,
                @RequestParam(value = "suggestionDateTime3", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dt3,
                @RequestParam(value = "suggestionDescription3", required = false) String desc3,
                Model model) {

            List<TimeSuggestion> suggestions = new ArrayList<>();
            if (dt1 != null) {
                suggestions.add(new TimeSuggestion(dt1, desc1));
            }
            if (dt2 != null) {
                suggestions.add(new TimeSuggestion(dt2, desc2));
            }
            if (dt3 != null) {
                suggestions.add(new TimeSuggestion(dt3, desc3));
            }

            if (suggestions.isEmpty()) {
                model.addAttribute("errorMessage", "Please provide at least one time suggestion.");
                model.addAttribute("appointmentCoordination", appointmentCoordination);
                return "create-appointment";
            }
            appointmentCoordination.setSuggestions(suggestions);
            appointmentCoordinationRepository.save(appointmentCoordination);
            return "redirect:/create?success";
        }
        @GetMapping(value = "/create", params = "success")
            public String showCreateFormWithSuccess(Model model) {
                model.addAttribute("appointmentCoordination", new AppointmentCoordination());
                model.addAttribute("successMessage", "Appointment coordination created successfully!");
                return "create-appointment";
            }
        }


