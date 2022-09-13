package com.messenger.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class Chat {

    @Id
    @GeneratedValue
    private Long id;
    @OneToMany(targetEntity = User.class)
    private List<User> participants;

    public User getFirstUser() {
        return participants.get(0);
    }

    public User getSecondUser() {
        return participants.get(1);
    }

    public boolean isUserParticipant(User user) {
        for (User participant : participants) {
            if (user.equals(participant)) {
                return true;
            }
        }
        return false;
    }

}
