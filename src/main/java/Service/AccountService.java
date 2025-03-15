package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        this.accountDAO = new AccountDAO(); 
    }
    
    // Might delete later-- this constructor is only useful for mocking at the moment
    public AccountService(AccountDAO accountDAO){
	    this.accountDAO= accountDAO;
    }

    public Account registerAccount(Account account){
        return meetsRegistrationRequirements(account) ? accountDAO.registerAccount(account) : null;
    }

    private boolean meetsRegistrationRequirements(Account account){
        return accountDAO.getAccountByUsername(account.getUsername()) == null &&
               !account.getUsername().isEmpty() &&
               !account.getUsername().isBlank() &&
               account.getPassword().length() >= 4;
    }

    public Account login(Account accountFromLoginRequest){
        Account possibleAccount = accountDAO.getAccountByUsername(accountFromLoginRequest.getUsername());
        
        if (possibleAccount.getUsername().equals(accountFromLoginRequest.getUsername()) &&
            possibleAccount.getPassword().equals(accountFromLoginRequest.getPassword()))
            return possibleAccount;
        else{
            return null;
        }
    }
}
