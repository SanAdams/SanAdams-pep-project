package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.command.Prepared;
import org.h2.command.ddl.PrepareProcedure;

import Util.ConnectionUtil;
import Model.Account;
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

            pstmt.setInt(1, message.getMessage_id());
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
}
