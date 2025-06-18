package de.hof_university.narenprak.cc2025.appointment_scheduler.model; 

import java.util.List;

public class AppointmentOverviewItem {
    private AppointmentCoordination coordination;
    private boolean hasReplies;
    private int replyCount;

    public AppointmentOverviewItem(AppointmentCoordination coordination, List<ParticipantResponse> responses) {
        this.coordination = coordination;
        if (responses != null && !responses.isEmpty()) {
            this.hasReplies = true;
            this.replyCount = responses.size();
        } else {
            this.hasReplies = false;
            this.replyCount = 0;
        }
    }

    public AppointmentCoordination getCoordination() {
        return coordination;
    }

    public boolean getHasReplies() {
        return hasReplies;
    }

    public int getReplyCount() {
        return replyCount;
    }
}