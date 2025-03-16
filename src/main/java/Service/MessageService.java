package Service;

import java.util.List;

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

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }
    public boolean meetsMessageRequirements(Message message){
        return !message.getMessage_text().isBlank() &&
                message.getMessage_text().length() < MAX_MESSAGE_LENGTH &&
                accountDAO.getAccountById(message.getPosted_by()) != null;
    }

    public Message getMessageGivenId(int id){
        return messageDAO.getMessageGivenId(id);
    }

    public Message deleteMessageGivenId(int id){
        Message messageAsStoredInDB = messageDAO.getMessageGivenId(id);
        if (messageAsStoredInDB != null){
            messageDAO.deleteMessageGivenId(id);
        }
        return messageAsStoredInDB;
    }
}
