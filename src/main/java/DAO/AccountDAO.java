package DAO;

import java.sql.*;
import Util.ConnectionUtil;
import Model.Account;

public class AccountDAO {
    private Connection connection;

    public AccountDAO(){
        this.connection = ConnectionUtil.getConnection();
    }
    /**
     * 
     * @return the account that's just been made
     */
    public Account registerAccount(Account account){
        try {
            String sql = "insert into account (username, password) values (?, ?);";
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            pstmt.setString(1, account.getUsername());
            pstmt.setString(2, account.getPassword());
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

    public Account getAccountByUsername(String username){
        try {
            String sql = "select * from account where username = ?;";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                Account account = new Account((int) rs.getLong("account_id"),
                                                    rs.getString("username"), 
                                                    rs.getString("password"));
                return account;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }

    public Account getAccountById(int id){
        try {
            String sql = "select * from account where account_id = ?;";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                Account account = new Account((int) rs.getLong("account_id"),
                                                    rs.getString("username"), 
                                                    rs.getString("password"));
                return account;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
}
