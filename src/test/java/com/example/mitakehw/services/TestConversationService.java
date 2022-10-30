package com.example.mitakehw.services;

import com.example.mitakehw.dao.ConversationDAO;
import com.example.mitakehw.models.Conversation;
import com.example.mitakehw.services.input.CreateConversationInput;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestConversationService {

    @BeforeEach
    public void setUp() {
    }
    @Test
    void testCreateConversation() {
        String conversationName = "TestConversation1";
        String createdUser = "46ea17d2-1dd1-4d50-b3f2-a80f5d103198";
        List<UUID> users = new ArrayList<>();
        users.add(UUID.fromString(createdUser));
        users.add(UUID.randomUUID());

        CreateConversationInput createConversationInput = new CreateConversationInput();
        createConversationInput.setConversationName(conversationName);
        createConversationInput.setCreatedUser(createdUser);
        createConversationInput.setUsers(users);
        ConversationService conversationService = new ConversationService();

        Conversation createdConversation = conversationService.createConversation(createConversationInput);

        Assert.assertEquals(conversationName,createdConversation.getConversationName());
        Assert.assertEquals(createdUser,createdConversation.getCreatedUser());
        Assert.assertEquals(users.size(),createdConversation.getUsers().size());

    }

}
