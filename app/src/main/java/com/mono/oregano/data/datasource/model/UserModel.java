package com.mono.oregano.data.datasource.model;

import com.google.firebase.firestore.DocumentSnapshot;
import com.mono.oregano.domain.domModel.Entities.UserEntity;

import java.util.Date;
import java.util.HashMap;

public class UserModel extends UserEntity {
    UserModel(String firstName, String midName, String lastName,
              String gender, String schoolNo, String email,
              String password, Date date){
        super(firstName,midName,lastName,gender, schoolNo,email, password,date);
    }

    UserModel fromSnapshot(DocumentSnapshot snapshot){
        return new UserModel(snapshot.getString(getFirstName()),
                snapshot.getString(getMidName()),snapshot.getString(getLastName()),
                snapshot.getString(getGender()),snapshot.getString(getSchoolNo()),
                snapshot.getString(getEmail()),snapshot.getString(getPassword()),
                snapshot.getDate(getCreatedAt().toString()));
    }

    HashMap<String, Object> toDocument(){
        HashMap<String, Object> user =  new HashMap<>();
        user.put("firstName",getFirstName());
        user.put("midName",getMidName());
        user.put("lastName",getLastName());
        user.put("gender",getGender());
        user.put("schoolNum",getSchoolNo());
        user.put("email", getEmail());
        user.put("password", getPassword());
        user.put("createdAt",getCreatedAt());
        user.put("following",getFollowing());
        user.put("memberOf", getMemberOf());
        return user;
    }
}
