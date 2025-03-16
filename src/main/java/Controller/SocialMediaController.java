package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;
    
    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageGivenIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageGivenIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageGivenIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesFromUserHandler);
        return app;
    }

    /**
     * 
     */
    private void registerHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Account addedAccount = accountService.registerAccount(objectMapper.readValue(ctx.body(), Account.class));

        if (addedAccount != null){
            ctx.json(objectMapper.writeValueAsString(addedAccount));
        }
        else{
            ctx.status(400);
        }
    }

    /**
     * Handler for login endpoint
     */
    private void loginHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        Account accountFromLoginRequest = accountService.login(objectMapper.readValue(ctx.body(), Account.class));
        if (accountFromLoginRequest != null){
            ctx.json(objectMapper.writeValueAsString(accountFromLoginRequest));
        }
        else{
            ctx.status(401);
        }
        
    }

    /**
     * 
     */
    private void createMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        Message newMessage = messageService.createMessage(objectMapper.readValue(ctx.body(), Message.class));
        if (newMessage != null){
            ctx.json(objectMapper.writeValueAsString(newMessage));
        }
        else{
            ctx.status(400);
        }
    }

    /**
     * 
     */
    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException{
        List<Message> messages = messageService.getAllMessages();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonSerializedMessages = objectMapper.writeValueAsString(messages);

        ctx.result(jsonSerializedMessages);
    }

    /**
     * 
     */
    private void getMessageGivenIdHandler(Context ctx) throws JsonProcessingException{
        String messageIdStr = ctx.pathParam("message_id");
        int messageId = Integer.parseInt(messageIdStr);
        
        ObjectMapper objectMapper = new ObjectMapper();
        Message message = messageService.getMessageGivenId(messageId);
        
        if (message != null){
            ctx.json(objectMapper.writeValueAsString(message));
        }
        else{
            ctx.status(200);
        }
    }

    /**
     * 
     */
     private void deleteMessageGivenIdHandler(Context ctx) throws JsonProcessingException{
        String messageIdStr = ctx.pathParam("message_id");
        int messageId = Integer.parseInt(messageIdStr);
        
        ObjectMapper objectMapper = new ObjectMapper();
        Message messageToDelete = messageService.deleteMessageGivenId(messageId);
        
        if (messageToDelete != null){
            ctx.json(objectMapper.writeValueAsString(messageToDelete));
        }
        else{
            ctx.status(200);
        }
    }

    /**
     * 
     */
    @SuppressWarnings("unchecked")
    private void updateMessageGivenIdHandler(Context ctx) throws JsonProcessingException{
        String messageIdStr = ctx.pathParam("message_id");
        int messageId = Integer.parseInt(messageIdStr);

        ObjectMapper objectMapper = new ObjectMapper();
        
        //Convert the request into a map and extract the message_text field
        Map<String, String> requestMap = objectMapper.readValue(ctx.body(), Map.class);
        String updatedMessageString = requestMap.get("message_text");
        
        Message updatedMessage = messageService.updateMessageGivenId(messageId, updatedMessageString);
        if (updatedMessage != null){
            ctx.json(objectMapper.writeValueAsString(updatedMessage));
        }
        else{
            ctx.status(400);
        }
    }

    /**
     * 
     */
    private void getAllMessagesFromUserHandler(Context ctx) throws JsonProcessingException{
        String messageIdStr = ctx.pathParam("account_id");
        int accountId = Integer.parseInt(messageIdStr);
        List<Message> messages = messageService.getAllMessagesFromUser(accountId);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonSerializedMessages = objectMapper.writeValueAsString(messages);

        ctx.result(jsonSerializedMessages);
    }
}