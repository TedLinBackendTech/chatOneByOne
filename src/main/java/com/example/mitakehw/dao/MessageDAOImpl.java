package com.example.mitakehw.dao;

import com.example.mitakehw.dao.dbconnection.DBConnection;
import com.example.mitakehw.dao.dbconnection.DBConnectionImpl;
import com.example.mitakehw.models.Message;
import com.example.mitakehw.utilities.TimeTool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MessageDAOImpl implements MessageDAO{
    private DBConnection dbConnection = new DBConnectionImpl();
    private static final String GET_MESSAGES_BY_CONVERSATION =
            "SELECT messages.*\n" +
            "FROM messages,message_committed_conversation\n" +
            "WHERE messages.message_id = message_committed_conversation.message_id AND message_committed_conversation.conversation_id=?;";
    private static final String INSERT_MESSAGE = "INSERT INTO messages " +
            "(message_id,from_user_id,to_user_id,content,created_at) " +
            "VALUES(?,?,?,?,?) ";
    private static final String INSERT_MESSAGE_COMMIT_CONVERSATION = "INSERT INTO message_committed_conversation " +
            "(message_id,conversation_id) " +
            "VALUES(?,?) ";
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

    @Override
    public void createMessage(Message message) {
        this.insert(message);
    }

    private void insert(Message message) {
        Connection connection = dbConnection.getConnection();
        try
        {
            connection.setAutoCommit(false);
            PreparedStatement psInsertMessage = connection.prepareStatement(INSERT_MESSAGE);
            psInsertMessage.setString(1,message.getMessageId().toString());
            psInsertMessage.setString(2,message.getFromUserId());
            psInsertMessage.setString(3,message.getToUserId());
            psInsertMessage.setString (4,message.getContent());
            psInsertMessage.setString (5, TimeTool.getCurrentTime());
            psInsertMessage.executeUpdate();

            PreparedStatement psInsertMessageCommitConversation = connection.prepareStatement(INSERT_MESSAGE_COMMIT_CONVERSATION);
            psInsertMessageCommitConversation.setString(1,message.getMessageId().toString());
            psInsertMessageCommitConversation.setString(2,message.getConversationId().toString());
            psInsertMessageCommitConversation.executeUpdate();

            // should it also update the conversation updateAt?


            // end transaction block, commit changes
            connection.commit();
            // good practice to set it back to default true
            connection.setAutoCommit(true);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
