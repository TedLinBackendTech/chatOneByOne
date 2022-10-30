package com.example.mitakehw.dao;

import com.example.mitakehw.models.Message;

import java.util.List;
import java.util.UUID;

public interface MessageDAO {
    List<Message> getByConversationId(UUID userId);

    void createMessage(Message message);
}
