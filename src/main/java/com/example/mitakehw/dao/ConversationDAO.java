package com.example.mitakehw.dao;

import com.example.mitakehw.models.Conversation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConversationDAO{
    Optional<Conversation> findByUsers(List<UUID> users);
    void save(Conversation conversation);
    Optional<Conversation> get(UUID conversationId);
    List<UUID>  getConversationIdsByUserId(UUID userId);

}
