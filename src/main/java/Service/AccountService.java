package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        this.accountDAO = new AccountDAO(); 
    }

    public Account registerAccount(Account account){
        return meetsRegistrationRequirements(account) ? accountDAO.registerAccount(account) : null;
    }

    private boolean meetsRegistrationRequirements(Account account){
        return accountDAO.getAccontByUsername(account.getUsername()) == null &&
               !account.getUsername().isEmpty() &&
               !account.getUsername().isBlank() &&
               account.getPassword().length() >= 4;
    }


}
