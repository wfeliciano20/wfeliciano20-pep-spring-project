package com.example.controller;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Dtos.AccountDto;
import com.example.Dtos.MessageDto;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService,MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody AccountDto accountDto){
        try {
            if(accountDto.getUsername().isBlank()|| accountDto.getPassword().length() < 4){
                return new ResponseEntity<Account>(HttpStatus.BAD_REQUEST);
            }
    
            else if(this.accountService.accountExists(accountDto.getUsername())){
                return new ResponseEntity<Account>(HttpStatus.CONFLICT);
            }else{
                Account registedAccount = this.accountService.registerAccount(accountDto);
    
                return new ResponseEntity<Account>(registedAccount, HttpStatus.OK);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
        
    }


    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody AccountDto accountDto){
        try {
            Account account = this.accountService.login(accountDto);
            if(account != null){
                return new ResponseEntity<Account>(account, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody MessageDto messageDto){
        if(messageDto.getMessageText().isBlank()|| messageDto.getMessageText().length() > 255){
            return new ResponseEntity<Message>( HttpStatus.BAD_REQUEST);
        }
        if(!this.accountService.accountExists(messageDto.getPostedBy())){
            return new ResponseEntity<Message>( HttpStatus.BAD_REQUEST);
        }
        Message createdMessage = this.messageService.createMessage(messageDto);

        if(createdMessage != null){
            return new ResponseEntity<Message>(createdMessage, HttpStatus.OK);
        }

        return new ResponseEntity<Message>( HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        return new ResponseEntity<List<Message>>(this.messageService.getAllMessages(), HttpStatus.OK);
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer message_id){

        try {
            Message message = this.messageService.getMessageById(message_id);
            if(message != null){
                return new ResponseEntity<Message>(message, HttpStatus.OK);
            }else{
                return new ResponseEntity<Message>(HttpStatus.OK);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<Message>(HttpStatus.OK);
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessageForUser(@PathVariable Integer account_id){
        List<Message> userMessages = this.messageService.getAllMessagesForUser(account_id);

        if(userMessages.size() > 0){
            return new ResponseEntity<List<Message>>(userMessages, HttpStatus.OK);
        }else{
            return new ResponseEntity<List<Message>>(userMessages,HttpStatus.OK);
        }
    }

    @PatchMapping("messages/{message_id}")
    public ResponseEntity<Integer> updateMessage(@PathVariable Integer message_id, @RequestBody MessageDto messageDto){
        
        if(messageDto.getMessageText().isBlank() || messageDto.getMessageText().length() > 255){
            return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
        }
        Integer rowsAffected = this.messageService.updateMessage(message_id, messageDto);
 
        if(rowsAffected != null && rowsAffected == 1){
            return new ResponseEntity<Integer>(1,HttpStatus.OK);
        }else{
            return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable Integer message_id){
        Integer rowsAffected = this.messageService.deleteMessaage(message_id);

        if( rowsAffected != null && rowsAffected == 1){
            return new ResponseEntity<Integer>(1,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<Integer>(HttpStatus.OK);
        }
    }


    

}
