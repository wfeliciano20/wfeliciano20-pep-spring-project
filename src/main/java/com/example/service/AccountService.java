package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.Dtos.AccountDto;
import com.example.entity.Account;
import com.example.exception.AppException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    /*
     * The registration will be successful if and only if the username
     * is not blank, the password is at least 4 characters long, and 
     * an Account with that username does not already exist. 
     */

     public Account registerAccount(AccountDto accountDto){
         Account savedAccount = this.accountRepository.save(new Account(accountDto.getUsername(),accountDto.getPassword()));
         return savedAccount;
     }

     public boolean accountExists(String username){
        return this.accountRepository.existsByUsername(username);
     }  

     public boolean accountExists(Integer id){
        return this.accountRepository.existsById(id);
     }

     public Account login(AccountDto accountDto){
        try {
            Optional<Account> account = this.accountRepository.findByUsername(accountDto.getUsername());

            if(account.isPresent()){
                if(account.get().getUsername().equals(accountDto.getUsername()) && account.get().getPassword().equals(accountDto.getPassword())){
                    return account.get();
                }
                return null;
            }else{
                return null;
            }
        } catch (Exception e) {
            throw new AppException(e.getMessage(),  HttpStatus.BAD_REQUEST );
        }
     }
}
