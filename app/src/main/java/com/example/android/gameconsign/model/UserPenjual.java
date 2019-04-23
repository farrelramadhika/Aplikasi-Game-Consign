package com.example.android.gameconsign.model;

/**
 * Created by TOSHIBA on 4/22/2019.
 */

public class UserPenjual {
    String userId;
    String password;
    String email;

    public UserPenjual(){
    }

    public UserPenjual(String userId, String password, String email){
        this.userId = userId;
        this.password = password;
        this.email = email;
    }

    public String getUserId(){
        return userId;}

    public String getPassword(){
        return  password;}

    public String getEmail(){
        return email;}
}
