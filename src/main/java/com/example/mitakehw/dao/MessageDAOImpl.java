package com.example.mitakehw.dao;

import com.example.mitakehw.dao.dbconnection.DBConnection;
import com.example.mitakehw.dao.dbconnection.DBConnectionImpl;
import com.example.mitakehw.models.Conversation;
import com.example.mitakehw.models.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MessageDAOImpl implements MessageDAO{
    private DBConnection dbConnection = new DBConnectionImpl();
    private static final String GET_MESSAGES_BY_CONVERSATION =
            "SELECT messages.*\n" +
            "FROM messages,message_committed_conversation\n" +
            "WHERE messages.message_id = message_committed_conversation.message_id AND message_committed_conversation.conversation_id=?;";

    @Override
    public List<Message> getByConversationId(UUID conversationId) {
        Connection connection = dbConnection.getConnection();
        ResultSet resultSet;
        List<Message> messages = new ArrayList<>();
        try
        {
            PreparedStatement psGetUserCommitConversation = connection.prepareStatement(GET_MESSAGES_BY_CONVERSATION);
            psGetUserCommitConversation.setString(1,conversationId.toString());
            resultSet = psGetUserCommitConversation.executeQuery();

            while(resultSet.next()) {
                Message message = new Message();
                message.setConversationId(UUID.fromString(resultSet.getString("conversation_id")));
                message.setFromUserId(resultSet.getString("from_user_id"));
                message.setToUserId(resultSet.getString("to_user_id"));
                message.setContent(resultSet.getString("content"));
                message.setCreatedAt(resultSet.getString("created_at"));
                messages.add(message);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return messages;
    }
}
