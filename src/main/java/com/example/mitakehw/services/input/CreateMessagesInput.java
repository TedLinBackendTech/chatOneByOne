package com.example.mitakehw.services.input;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateMessagesInput {
    private UUID conversationsId;
    private String fromUserId;
    private String toUserId;
    private String content;
}
