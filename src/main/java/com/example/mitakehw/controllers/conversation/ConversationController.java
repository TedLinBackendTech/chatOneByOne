package com.example.mitakehw.controllers.conversation;

import com.example.mitakehw.services.ConversationService;
import com.example.mitakehw.services.input.CreateConversationInput;
import com.example.mitakehw.services.input.GetConversationMessagesInput;
import com.example.mitakehw.services.input.GetConversationsInput;
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
        // List<UUID>
        Map map = new HashMap();
        map.put("conversationIds",conversationService.getConversationsByUser(getConversationInput));
        String returnObject = new Gson().toJson(map);
        return ResponseEntity.ok(returnObject);
    }


    @GetMapping(path="/conversations/{conversationsId}/messages", consumes = "application/json", produces = "application/json")
    public ResponseEntity getConversationContent(@PathVariable("conversationsId") String conversationsId
            ,@RequestBody String conversationInfo){
        int limit = 0;
        String name="";
        try {
            JSONObject conversationJSON = new JSONObject(conversationInfo);
            name = conversationJSON.getString("conversation_name");
            limit = conversationJSON.getInt("limit");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        GetConversationMessagesInput input = new GetConversationMessagesInput();
        input.setConversationId(UUID.fromString(conversationsId));
        input.setConversationName(name);
        input.setLimit(limit);


        Map map = new HashMap();
        map.put("conversationIds",UUID.fromString(conversationsId));
        map.put("messages",conversationService.getMessagesByConversationId(input));
        String returnObject = new Gson().toJson(map);

        return ResponseEntity.ok(returnObject);
    }


}
