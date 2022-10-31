package com.example.mitakehw.services.input;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
@Getter
@Setter
public class GetConversationMessagesInput {
    private UUID conversationId;
    private String conversationName;

}
