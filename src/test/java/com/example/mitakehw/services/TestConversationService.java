package com.example.mitakehw.services;

import com.example.mitakehw.dao.ConversationDAO;
import com.example.mitakehw.models.Conversation;
import com.example.mitakehw.models.Message;
import com.example.mitakehw.services.input.CreateConversationInput;
import com.example.mitakehw.services.input.CreateMessagesInput;
import com.example.mitakehw.services.input.GetConversationMessagesInput;
import com.example.mitakehw.services.input.GetConversationsInput;
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
    @Test
    void testGetConversationIds() {
        String user = "46ea17d2-1dd1-4d50-b3f2-a80f5d103198";
        GetConversationsInput getConversationInput = new GetConversationsInput();
        getConversationInput.setUser(user);

        ConversationService conversationService = new ConversationService();
        List<UUID> result = conversationService.getConversationsByUser(getConversationInput);
        Assert.assertEquals(4,result.size());
    }
    @Test
    void testCreateMessage() {
        String conversationId = "b253b6ef-3e47-4b0c-8fac-4ee8bc582878";
        String fromUserId="beac28d8-f7d9-46cc-b8c6-ba69a6d76b3b";
        String toUserId="9f8b68cd-3292-4789-8d5f-46b872509bd7";
        String content="另一筆訊息" + Math.random();

        CreateMessagesInput input = new CreateMessagesInput();
        input.setConversationsId(UUID.fromString(conversationId));
        input.setFromUserId(fromUserId);
        input.setToUserId(toUserId);
        input.setContent(content);

        ConversationService conversationService = new ConversationService();
        UUID messageID = conversationService.createMessage(input);

    }

    @Test
    void testGetMessage() {
        String conversationId = "b253b6ef-3e47-4b0c-8fac-4ee8bc582878";

        GetConversationMessagesInput input = new GetConversationMessagesInput();
        input.setConversationId(UUID.fromString(conversationId));

        ConversationService conversationService = new ConversationService();
        List<Message> messages = conversationService.getMessagesByConversationId(input);
        for (Message m :
             messages) {
            System.out.println(m.getContent());
        }
    }
}
