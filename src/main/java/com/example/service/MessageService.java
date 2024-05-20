package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.Dtos.MessageDto;
import com.example.entity.Message;
import com.example.exception.AppException;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public Message createMessage(MessageDto messageDto){
        try {
            return this.messageRepository.save(new Message(messageDto.getPostedBy(),messageDto.getMessageText(),messageDto.getTimePostedEpoch()));
        } catch (Exception e) {
            throw new AppException(e.getMessage(),  HttpStatus.BAD_REQUEST );
        }
    }

    public List<Message> getAllMessages(){
        List<Message> messages = new ArrayList<>();
        try {
            messages = this.messageRepository.findAll();
        } catch (Exception e) {
            throw new AppException(e.getMessage(),  HttpStatus.BAD_REQUEST );
        }
       return messages;
    }

    public List<Message> getAllMessagesForUser(Integer id){
        List<Message> messages = new ArrayList<>();

        try {
            messages = this.messageRepository.findAllByPostedBy(id);
        } catch (Exception e) {
            throw new AppException(e.getMessage(),  HttpStatus.BAD_REQUEST );
        }

        return messages;
    }

    public Message getMessageById(Integer id){
        try {
            Message message = this.messageRepository.findById(id).orElseThrow(()-> new AppException("Message with id: " + id + " was not found", HttpStatus.NOT_FOUND));
            return message;
        } catch (Exception e) {
            throw new AppException(e.getMessage(),  HttpStatus.BAD_REQUEST );
        }
    }

    public Integer updateMessage(Integer id, MessageDto messageDto){
        
        try {
            Optional<Message> message = this.messageRepository.findById(id);
            if(message.isPresent()){
                message.get().setMessageText(messageDto.getMessageText());
                this.messageRepository.save(message.get());
                return 1;
            }else{
                return null;
            }
        } catch (Exception e) {
            throw new AppException(e.getMessage(),  HttpStatus.BAD_REQUEST );
        }
    }

    public Integer deleteMessaage(Integer id){
        try {
           Optional<Message> message = this.messageRepository.findById(id);

           if(message.isPresent()){
                this.messageRepository.delete(message.get());
                return 1;
           }else{
            return null;
           }
        } catch (Exception e) {
            throw new AppException(e.getMessage(),  HttpStatus.BAD_REQUEST );
        }
    }

}
