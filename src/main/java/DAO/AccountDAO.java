package DAO;

import java.sql.*;
import Util.ConnectionUtil;
import Model.Account;

public class AccountDAO {
    private Connection connection = ConnectionUtil.getConnection();

    /**
     * 
     * @return the account that's just been made
     */
    public Account registerAccount(Account account){
        try {
            String sql = "insert into account values (?, ?, ?);";
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            pstmt.setInt(1, account.getAccount_id());
            pstmt.setString(2, account.getUsername());
            pstmt.setString(3, account.getPassword());
            pstmt.executeUpdate();

            ResultSet pkrs = pstmt.getGeneratedKeys();
            if(pkrs.next()){
                int generatedId = (int) pkrs.getLong(1);
                return new Account(generatedId, account.getUsername(), account.getPassword());
            }
           
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

}
