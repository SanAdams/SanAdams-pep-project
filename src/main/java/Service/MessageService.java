package Service;

import DAO.MessageDAO;
import DAO.AccountDAO;
import Model.Message;

public class MessageService {

    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService(){
        this.messageDAO = new MessageDAO(); 
        this.accountDAO = new AccountDAO();
    }
    
    public Message createMessage(Message message){
        return meetsMessageRequirements(message) ? messageDAO.insertMessage(message) : null;
    }

    public boolean meetsMessageRequirements(Message message){
        return !message.getMessage_text().isBlank() &&
                message.getMessage_text().length() < 255 &&
                accountDAO.getAccountById(message.getPosted_by()) != null;
    }
}
