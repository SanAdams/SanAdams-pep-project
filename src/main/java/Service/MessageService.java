package Service;

import DAO.MessageDAO;
import DAO.AccountDAO;
import Model.Message;

public class MessageService {

    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    private final int MAX_MESSAGE_LENGTH = 255;

    public MessageService(){
        this.messageDAO = new MessageDAO(); 
        this.accountDAO = new AccountDAO();
    }
    
    public Message createMessage(Message message){
        return meetsMessageRequirements(message) ? messageDAO.insertMessage(message) : null;
    }

    public boolean meetsMessageRequirements(Message message){
        return !message.getMessage_text().isBlank() &&
                message.getMessage_text().length() < MAX_MESSAGE_LENGTH &&
                accountDAO.getAccountById(message.getPosted_by()) != null;
    }

    public Message getMessageGivenId(int id){
        return messageDAO.getMessageGivenId(id);
    }

    public Message deleteMessageGivenId(Message message){
        Message messageAsStoredInDB = messageDAO.getMessageGivenId(message.getMessage_id());
        if (messageAsStoredInDB != null){
            messageDAO.deleteMessageGivenId(messageAsStoredInDB.getMessage_id());
        }
        return messageAsStoredInDB;
    }
}
