package com.messenger.repository;

import com.messenger.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query(value = "select * from chat_participants where participants_id = ?1", nativeQuery = true)
    List<Chat> findAllByParticipantsContaining(Long user);

}
