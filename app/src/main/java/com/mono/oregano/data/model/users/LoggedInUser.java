package com.mono.oregano.data.model.users;


import com.mono.oregano.data.model.UserModel;

public class LoggedInUser extends UserModel {
    public LoggedInUser(String id, String email){
        super(id ,email);
    }
}
