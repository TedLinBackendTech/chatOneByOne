package com.example.mitakehw.services;

import com.example.mitakehw.dao.ConversationDAOImpl;
import com.example.mitakehw.exceptions.ConversationAlreadyCreatedException;
import com.example.mitakehw.models.Conversation;
import com.example.mitakehw.dao.ConversationDAO;
import com.example.mitakehw.services.input.CreateConversationInput;
import com.example.mitakehw.services.input.GetConversationsInput;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConversationService {
    private final ConversationDAO conversationDao = new ConversationDAOImpl();


    public Conversation createConversation(CreateConversationInput input) {
//        if(isConversationCreated(input.getUsers())){
//            throw new ConversationAlreadyCreatedException(input.getCreatedUser());
//        }
        Conversation conversation = new Conversation(input.getConversationName(),input.getUsers());
        conversation.setCreatedUser(input.getCreatedUser());
        conversationDao.save(conversation);
        Optional<Conversation> createdConversation = conversationDao.get(conversation.getConversationId());
        if(createdConversation.isPresent()){
            return createdConversation.get();
        }else{
            throw new RuntimeException("Conversation create fail");
        }


    }
    private boolean isConversationCreated(List<UUID> users){
        Optional<Conversation> conversation = conversationDao.findByUsers(users);
        if(conversation.isPresent()){
            return true;
        }
        return false;
    }

    public List<UUID> getConversationsByUser(GetConversationsInput input) {
        List<UUID> conversationIds = conversationDao.getConversationIdsByUserId(UUID.fromString(input.getUser()));
        return  conversationIds;
    }
}
