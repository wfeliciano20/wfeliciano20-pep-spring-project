package com.example.Dtos;

public class AccountDto {
    
     /**
     * A username for this Account (must be unique and not blank)
     */
    private String username;
    /**
     * A password for this account (must be over 4 characters)
     */
    private String password;

    public AccountDto(){}

    public AccountDto(String username, String password){
        this.username = username;
        this.password = password;
    }

     /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @return username
     */
    public String getUsername() {
        return username;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @return password
     */
    public String getPassword() {
        return password;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
