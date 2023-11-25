package com.example.Java4.entity;

public class RegistrationDTO {
    private String email;
    private String username;
    private String password;

    public RegistrationDTO(){
        super();
    }

    public RegistrationDTO(String email, String username, String password){
        super();
        this.email = email;
        this.username = username;
        this.password = password;
    }
    public String getEmail(){
        return this.email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getUsername(){
        return this.username;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String toString(){
        return "Registration info: email" + this.email + ", username: "+ this.username + ", password: " + this.password;
    }
}
