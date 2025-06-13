package de.hof_university.narenprak.cc2025.appointment_scheduler.controller;

import de.hof_university.narenprak.cc2025.appointment_scheduler.model.AppointmentCoordination;
import de.hof_university.narenprak.cc2025.appointment_scheduler.model.ParticipantResponse;
import de.hof_university.narenprak.cc2025.appointment_scheduler.model.TimeSuggestion;
import de.hof_university.narenprak.cc2025.appointment_scheduler.model.AppointmentOverviewItem;
import de.hof_university.narenprak.cc2025.appointment_scheduler.repository.AppointmentCoordinationRepository;
import de.hof_university.narenprak.cc2025.appointment_scheduler.repository.ParticipantResponseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller
public class AppointmentController {
    @Autowired
    private AppointmentCoordinationRepository appointmentCoordinationRepository;
    @Autowired
    private ParticipantResponseRepository participantResponseRepository;

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

        @GetMapping("/reply/{coordinationId}")
            public String showReplyForm(@PathVariable String coordinationId, Model model) {
            Optional<AppointmentCoordination> optCoordination = appointmentCoordinationRepository.findById(coordinationId);
            if (optCoordination.isPresent()) {
            AppointmentCoordination coordination = optCoordination.get();
            model.addAttribute("coordination", coordination);
            model.addAttribute("participantResponse", new ParticipantResponse(coordinationId, ""));
            return "reply-appointment";
        } else {
            model.addAttribute("errorMessage", "Appointment coordination not found with ID: " + coordinationId);
            model.addAttribute("appointmentCoordination", new AppointmentCoordination());
            return "create-appointment";
        }
    }
        @PostMapping("/reply/{coordinationId}")
            public String processReply(@PathVariable String coordinationId,
                               @ModelAttribute ParticipantResponse participantResponse,
                               @RequestParam Map<String, String> allRequestParams,
                               Model model) {
            participantResponse.setCoordinationId(coordinationId);
            Map<Integer, Boolean> availabilityMap = new HashMap<>();
            int numberOfSuggestions = 0;
            Optional<AppointmentCoordination> optCoord = appointmentCoordinationRepository.findById(coordinationId);
            if (optCoord.isPresent()) {
            numberOfSuggestions = optCoord.get().getSuggestions().size();
            } else {
            model.addAttribute("errorMessage", "Appointment coordination not found. Reply cannot be processed.");
            model.addAttribute("appointmentCoordination", new AppointmentCoordination());
            return "create-appointment";
        }

        for (int i = 0; i < numberOfSuggestions; i++) {
            String availabilityParamKey = "availability_" + i;
            boolean isAvailable = "true".equalsIgnoreCase(allRequestParams.get(availabilityParamKey)) ||
                    "on".equalsIgnoreCase(allRequestParams.get(availabilityParamKey));
            availabilityMap.put(i, isAvailable);
        }
        participantResponse.setAvailability(availabilityMap);

        if (participantResponse.getParticipantName() == null || participantResponse.getParticipantName().trim().isEmpty()) {
            model.addAttribute("replyError", "Participant name cannot be empty.");
            if (optCoord.isPresent()) {
                model.addAttribute("coordination", optCoord.get());
                model.addAttribute("participantResponse", participantResponse);
            } else {
                model.addAttribute("errorMessage", "Appointment coordination not found. Reply form cannot be displayed.");
                model.addAttribute("appointmentCoordination", new AppointmentCoordination());
                return "create-appointment";
            }
            return "reply-appointment";
        }
        participantResponseRepository.save(participantResponse);
        return "redirect:/reply/" + coordinationId + "?replied=true";
    }
        @GetMapping(value = "/reply/{coordinationId}", params = "replied")
            public String showReplyFormWithSuccess(@PathVariable String coordinationId, Model model) {
            Optional<AppointmentCoordination> optCoordination = appointmentCoordinationRepository.findById(coordinationId);
            if (optCoordination.isPresent()) {
            model.addAttribute("coordination", optCoordination.get());
            model.addAttribute("participantResponse", new ParticipantResponse(coordinationId, ""));
            model.addAttribute("replySuccessMessage", "Thank you! Your response has been recorded.");
            return "reply-appointment";
             } else {
            model.addAttribute("errorMessage", "Appointment coordination not found with ID: " + coordinationId);
            model.addAttribute("appointmentCoordination", new AppointmentCoordination());
            return "create-appointment";
        }
    }
    @GetMapping("/overview") // This tells Spring to use this method for GET requests to /overview
    public String showOverviewPage(Model model) {
        List<AppointmentCoordination> allCoordinations = appointmentCoordinationRepository.findAll();
        List<AppointmentOverviewItem> overviewItems = new ArrayList<>(); // Assuming AppointmentOverviewItem DTO is created

        for (AppointmentCoordination coordination : allCoordinations) {
            List<ParticipantResponse> responses = participantResponseRepository.findByCoordinationId(coordination.getId());
            overviewItems.add(new AppointmentOverviewItem(coordination, responses));
        }

        model.addAttribute("overviewItems", overviewItems);
        return "overview-page"; // This must match the name of your Thymeleaf HTML file
    }
    @GetMapping("/results/{coordinationId}")
    public String showResultsPage(@PathVariable String coordinationId, Model model) {
        Optional<AppointmentCoordination> optCoordination = appointmentCoordinationRepository.findById(coordinationId);

        if (optCoordination.isPresent()) {
            AppointmentCoordination coordination = optCoordination.get();
            List<ParticipantResponse> responses = participantResponseRepository.findByCoordinationId(coordinationId);

            model.addAttribute("coordination", coordination);
            model.addAttribute("responses", responses);

            return "results-page"; // Name der neuen Thymeleaf-Vorlage
        } else {
            model.addAttribute("errorMessage", "Appointment coordination not found with ID: " + coordinationId);
            model.addAttribute("overviewItems", new ArrayList<>());
            return "overview-page"; //
        }
    }

}



