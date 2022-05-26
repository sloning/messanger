package com.messenger.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class Chat {

    @Id
    private String id;
    private List<String> participants;

    public String getFirstUser() {
        return participants.get(0);
    }

    public String getSecondUser() {
        return participants.get(1);
    }

    public boolean isUserParticipant(String userId) {
        for (String participant : participants) {
            if (userId.equals(participant)) {
                return true;
            }
        }
        return false;
    }

}
