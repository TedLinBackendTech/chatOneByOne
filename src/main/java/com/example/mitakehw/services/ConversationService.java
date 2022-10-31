package com.example.mitakehw.services;

import com.example.mitakehw.dao.daoImpl.ConversationDAOImpl;
import com.example.mitakehw.dao.MessageDAO;
import com.example.mitakehw.dao.daoImpl.MessageDAOImpl;
import com.example.mitakehw.models.Conversation;
import com.example.mitakehw.dao.ConversationDAO;
import com.example.mitakehw.models.Message;
import com.example.mitakehw.services.input.CreateConversationInput;
import com.example.mitakehw.services.input.CreateMessagesInput;
import com.example.mitakehw.services.input.GetConversationMessagesInput;
import com.example.mitakehw.services.input.GetConversationsInput;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConversationService {
    private final ConversationDAO conversationDao = new ConversationDAOImpl();
    private final MessageDAO messageDao = new MessageDAOImpl();


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

    public List<Message> getMessagesByConversationId(GetConversationMessagesInput input) {
        return messageDao.getByConversationId(input.getConversationId());

    }

    public UUID createMessage(CreateMessagesInput input) {
        Message message = new Message();
        message.setConversationId(input.getConversationsId());
        message.setFromUserId(input.getFromUserId());
        message.setToUserId(input.getToUserId());
        message.setContent(input.getContent());

        messageDao.createMessage(message);

        return message.getMessageId();
    }
}
