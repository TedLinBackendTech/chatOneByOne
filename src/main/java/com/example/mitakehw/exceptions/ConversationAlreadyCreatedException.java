package com.example.mitakehw.exceptions;

import java.util.List;
import java.util.UUID;

public class ConversationAlreadyCreatedException extends RuntimeException{
    public ConversationAlreadyCreatedException(String createdUser) {
        super("Conversation created by:"+ createdUser + "is already created" );
    }
}
