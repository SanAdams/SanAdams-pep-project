package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;
import java.util.ArrayList;

import org.h2.command.Prepared;
import org.h2.command.ddl.PrepareProcedure;

import Util.ConnectionUtil;

import Model.Message;

public class MessageDAO {
    private Connection connection;

    public MessageDAO(){
        this.connection = ConnectionUtil.getConnection();
    }

    public Message insertMessage(Message message){
        try {
            String sql = "insert into message(posted_by, message_text, time_posted_epoch) values(?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, message.getPosted_by());
            pstmt.setString(2, message.getMessage_text());
            pstmt.setLong(3, message.getTime_posted_epoch());
            pstmt.executeUpdate();
            
            ResultSet pkrs = pstmt.getGeneratedKeys();
            if(pkrs.next()){
                int generatedId = (int) pkrs.getLong(1);
                return new Message(generatedId, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }

    public Message getMessageGivenId(int id){
        try {
            String sql = "select * from message where message_id = ?;";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                Message message = new Message((int) rs.getLong("message_id"),
                                            rs.getInt("posted_by"),
                                            rs.getString("message_text"),
                                            rs.getLong("time_posted_epoch"));
                return message;
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void deleteMessageGivenId(int id){
        try {
            String sql = "delete from message where message_id = ?;";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Message> getAllMessages(){
        List<Message> messages = new ArrayList<Message>();
        
        try {
            String sql = "select * from message;";
            Statement pstmt = connection.createStatement();
            
            ResultSet rs = pstmt.executeQuery(sql);
            while(rs.next()){
                Message message = new Message((int) rs.getLong("message_id"),
                                            rs.getInt("posted_by"),
                                            rs.getString("message_text"),
                                            rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return messages;
    }

    public void updateMessageGivenId(int id, String updatedMessageString){
        try {
            String sql = "update message " +
                         "set message_text = ? " +
                         "where message_id = ?;";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, updatedMessageString);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Message> getAllMessagesFromUser(int account_id){
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "select * from message " +
                         "join account " +
                         "on message.posted_by = account.account_id " +
                         "where account.account_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, account_id);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                Message message = new Message((int) rs.getLong("message_id"),
                                            rs.getInt("posted_by"),
                                            rs.getString("message_text"),
                                            rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return messages;
    }
}
