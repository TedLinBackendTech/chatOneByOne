package com.example.mitakehw.controllers.conversation;

import com.example.mitakehw.services.ConversationService;
import com.example.mitakehw.services.input.CreateConversationInput;
import com.example.mitakehw.services.input.CreateMessagesInput;
import com.example.mitakehw.services.input.GetConversationMessagesInput;
import com.example.mitakehw.services.input.GetConversationsInput;
import com.example.mitakehw.utilities.TimeTool;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

import java.util.*;

@RestController
@CrossOrigin//CrossOrigin is about setting where the backend would receive the request from
public class ConversationController {
    private ConversationService conversationService = new ConversationService();


    @PostMapping(path="/conversations", consumes = "application/json", produces = "application/json")
    public ResponseEntity createConversation(@RequestBody String conversationInfo){
        String conversationName = "";
        String createdUser = "";
        List<UUID> users = new ArrayList<>();
        try {
            JSONObject conversationJSON = new JSONObject(conversationInfo);
            conversationName = conversationJSON.getString("conversation_name");
            createdUser = conversationJSON.getString("created_user");

            JSONArray usersJson = conversationJSON.getJSONArray("users");
            for (int i=0; i<usersJson.length(); i++) {
                users.add(UUID.fromString(usersJson.getString(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CreateConversationInput createConversationInput = new CreateConversationInput();
        createConversationInput.setConversationName(conversationName);
        createConversationInput.setCreatedUser(createdUser);
        createConversationInput.setUsers(users);

        return ResponseEntity.ok(conversationService.createConversation(createConversationInput));
    }

    @GetMapping(path="/conversations", consumes = "application/json", produces = "application/json")
    public ResponseEntity getConversations(@RequestBody String userInfo) {
        String user="";
        try {
            JSONObject userJSON = new JSONObject(userInfo);
            user = userJSON.getString("user");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        GetConversationsInput getConversationInput = new GetConversationsInput();
        getConversationInput.setUser(user);
        Map map = new HashMap();
        map.put("conversationIds",conversationService.getConversationsByUser(getConversationInput));
        String returnObject = new Gson().toJson(map);
        return ResponseEntity.ok(returnObject);
    }


    @GetMapping(path="/conversations/{conversationId}/messages", consumes = "application/json", produces = "application/json")
    public ResponseEntity getMessages(@PathVariable("conversationId") String conversationId, @RequestBody String conversationInfo){
        String name="";
        try {
            JSONObject conversationJSON = new JSONObject(conversationInfo);
            name = conversationJSON.getString("conversation_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        GetConversationMessagesInput input = new GetConversationMessagesInput();
        input.setConversationId(UUID.fromString(conversationId));
        input.setConversationName(name);


        Map map = new HashMap();
        map.put("conversationIds",UUID.fromString(conversationId));
        map.put("messages",conversationService.getMessagesByConversationId(input));
        String returnObject = new Gson().toJson(map);

        return ResponseEntity.ok(returnObject);
    }

    @PostMapping(path="/conversations/{conversationId}/messages", consumes = "application/json", produces = "application/json")
    public ResponseEntity createMessage(@PathVariable("conversationId") String conversationId, @RequestBody String messageInfo){
        String fromUserId="";
        String toUserId="";
        String content="";
        try {
            JSONObject conversationJSON = new JSONObject(messageInfo);
            fromUserId = conversationJSON.getString("from_user_id");
            toUserId = conversationJSON.getString("to_user_id");
            content = conversationJSON.getString("content");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        CreateMessagesInput input = new CreateMessagesInput();
        input.setConversationsId(UUID.fromString(conversationId));
        input.setFromUserId(fromUserId);
        input.setToUserId(toUserId);
        input.setContent(content);


        Map map = new HashMap();
        map.put("created_at", TimeTool.getCurrentTime());
        map.put("messageId", conversationService.createMessage(input));
        String returnObject = new Gson().toJson(map);
//        // return messageid and timestamp
        return ResponseEntity.ok(returnObject);
    }

}
