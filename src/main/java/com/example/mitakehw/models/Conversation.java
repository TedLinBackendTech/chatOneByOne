package com.example.mitakehw.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
public class Conversation {
    private int id;

    private UUID conversationId;

    private String conversationName;
    private String createdUser;

    private List<UUID> users;

    private List<Message> messages;

    private String updatedAt;
    private String createdAt;

    public Conversation(String conversationName, List<UUID> users) {
        conversationId = UUID.randomUUID();
        this.conversationName = conversationName;
        this.users = users;
    }
    public Conversation(){

    }
}
