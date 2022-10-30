package com.example.mitakehw.models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Message {

    private UUID conversationId;
    // one by one
    private String fromUserId;
    private String toUserId;
    private String content;
    private String createdAt;
//    private String type; // define enum
// will there be different type?

}
