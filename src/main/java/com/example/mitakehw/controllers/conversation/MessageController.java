package com.example.mitakehw.controllers.conversation;

import com.example.mitakehw.controllers.conversation.websocket.MessageTemplate;
import com.example.mitakehw.controllers.conversation.websocket.WebSocketSessions;
import com.example.mitakehw.models.Message;
import com.example.mitakehw.utilities.TimeTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin
public class MessageController {
    @Autowired
    private WebSocketSessions sessions;


    // receive from /chat and sent to BROADCAST_DESTINATION
    @MessageMapping("/chat")
    @SendTo(MessageTemplate.BROADCAST_DESTINATION)
    public Message send(final Message message) throws Exception {
        final String time = TimeTool.getCurrentTime();
        return message;
    }
}
