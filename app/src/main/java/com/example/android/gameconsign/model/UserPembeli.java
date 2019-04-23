package com.example.android.gameconsign.model;

/**
 * Created by TOSHIBA on 4/22/2019.
 */

public class UserPembeli {
    String userId;
    String password;
    String email;

    public UserPembeli(){
    }

    public UserPembeli(String userId, String username, String email){
        this.userId = userId;
        this.password = password;
        this.email = email;
    }

    public String getUserId(){ return userId;}

    public String getUsername(){return  password;}

    public String getEmail(){return email;}

}
