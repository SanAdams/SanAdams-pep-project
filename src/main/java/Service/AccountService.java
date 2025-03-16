package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    private final int MIN_PASSWORD_LENGTH = 4;

    public AccountService(){
        this.accountDAO = new AccountDAO(); 
    }

    public Account registerAccount(Account account){
        return meetsRegistrationRequirements(account) ? accountDAO.registerAccount(account) : null;
    }

    private boolean meetsRegistrationRequirements(Account account){
        return accountDAO.getAccountByUsername(account.getUsername()) == null &&
               !account.getUsername().isEmpty() &&
               !account.getUsername().isBlank() &&
               account.getPassword().length() >= MIN_PASSWORD_LENGTH;
    }

    public Account login(Account accountFromLoginRequest){
        Account possibleAccount = accountDAO.getAccountByUsername(accountFromLoginRequest.getUsername());
        
        if (possibleAccount != null){
            if (possibleAccount.getPassword().equals(accountFromLoginRequest.getPassword()))
                return possibleAccount;
        }
        return null;
    }
    
}
