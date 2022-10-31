package com.example.mitakehw.dao.daoImpl;

import com.example.mitakehw.dao.ConversationDAO;
import com.example.mitakehw.dao.dbconnection.DBConnection;
import com.example.mitakehw.dao.dbconnection.DBConnectionImpl;
import com.example.mitakehw.models.Conversation;
import com.example.mitakehw.utilities.TimeTool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ConversationDAOImpl implements ConversationDAO {
    private DBConnection dbConnection = new DBConnectionImpl();

    private static final String INSERT_CONVERSATION = "INSERT INTO conversation " +
            "(conversation_id,conversation_name,created_user,created_at,updated_at) " +
            "VALUES(?,?,?,?,?) ";
    private static final String INSERT_USER_COMMIT_CONVERSATION = "INSERT INTO user_committed_conversation " +
            "(user_id,conversation_id) " +
            "VALUES(?,?) ";

    private static final String GET_CONVERSATION = "SELECT * FROM conversation WHERE conversation_id=?";
    private static final String GET_USERS_FROM_COMMIT_CONVERSATION = "SELECT user_id FROM user_committed_conversation WHERE conversation_id=?";
    private static final String GET_CONVERSATIONS_BY_USER_FROM_COMMIT_CONVERSATION = "SELECT conversation_id FROM user_committed_conversation WHERE user_id=?";

    @Override
    public Optional<Conversation> findByUsers(List<UUID> users) {
        return Optional.empty();
    }

    @Override
    public void save(Conversation conversation) {
        this.insert(conversation);
    }

    @Override
    public Optional<Conversation> get(UUID conversationId) {
        Connection connection = dbConnection.getConnection();
        ResultSet resultSet;
        Conversation conversation= new Conversation();
        try
        {
            connection.setAutoCommit(false);
            PreparedStatement psGetConversation = connection.prepareStatement(GET_CONVERSATION);
            psGetConversation.setString(1,conversationId.toString());
            resultSet = psGetConversation.executeQuery();
            if(resultSet.next()) {
                conversation.setConversationId(UUID.fromString(resultSet.getString("conversation_id")));
                conversation.setConversationName(resultSet.getString("conversation_name"));
                conversation.setCreatedUser(resultSet.getString("created_user"));
                conversation.setCreatedAt(resultSet.getString("created_at"));
                conversation.setUpdatedAt(resultSet.getString("updated_at"));
            }

            PreparedStatement psGetUserCommitConversation = connection.prepareStatement(GET_USERS_FROM_COMMIT_CONVERSATION);
            psGetUserCommitConversation.setString(1,conversationId.toString());
            resultSet = psGetUserCommitConversation.executeQuery();
            List<UUID> users = new ArrayList<>();
            while(resultSet.next()) {
                users.add(UUID.fromString(resultSet.getString("user_id")));
            }
            conversation.setUsers(users);

            // end transaction block, commit changes
            connection.commit();
            // good practice to set it back to default true
            connection.setAutoCommit(true);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }



        return Optional.of(conversation);


    }

    @Override
    public List<UUID> getConversationIdsByUserId(UUID userId) {
        Connection connection = dbConnection.getConnection();
        ResultSet resultSet;
        List<UUID> conversationIds= new ArrayList<>();
        try
        {
            PreparedStatement psGetCommitConversationIds = connection.prepareStatement(GET_CONVERSATIONS_BY_USER_FROM_COMMIT_CONVERSATION);
            psGetCommitConversationIds.setString(1,userId.toString());
            resultSet = psGetCommitConversationIds.executeQuery();
            while(resultSet.next()) {
                conversationIds.add(UUID.fromString(resultSet.getString("conversation_id")));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conversationIds;

    }




    private void insert(Conversation object){
        Connection connection = dbConnection.getConnection();
        try
        {
            connection.setAutoCommit(false);
            PreparedStatement psInsertConversation = connection.prepareStatement(INSERT_CONVERSATION);
            psInsertConversation.setString(1,object.getConversationId().toString());
            psInsertConversation.setString(2,object.getConversationName());
            psInsertConversation.setString(3,object.getCreatedUser());
            psInsertConversation.setString (4, TimeTool.getCurrentTime());
            psInsertConversation.setString (5,TimeTool.getCurrentTime());
            psInsertConversation.executeUpdate();
            for(UUID user:object.getUsers()){
                PreparedStatement psInsertUserCommitConversation = connection.prepareStatement(INSERT_USER_COMMIT_CONVERSATION);
                psInsertUserCommitConversation.setString(1,user.toString());
                psInsertUserCommitConversation.setString(2,object.getConversationId().toString());
                psInsertUserCommitConversation.executeUpdate();
            }

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
