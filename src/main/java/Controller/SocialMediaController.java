package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
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
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
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
    private void getAllMessagesHandler(Context ctx){

    }

    /**
     * 
     */
    private void getMessageGivenIdHandler(Context ctx){

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
     * @param ctx
     */
    private void updateMessageGivenIdHandler(Context ctx){

    }

    /**
     * 
     */
    private void getAllMessagesFromUserHandler(Context ctx){

    }
}