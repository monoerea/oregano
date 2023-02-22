package com.mono.oregano.data.model;

import com.google.firebase.firestore.DocumentSnapshot;
import com.mono.oregano.domain.entity.Users.UserEntity;

import java.util.Date;

public class UserModel extends UserEntity implements Model {
    public UserModel(String id, String firstName, String midName, String lastName,
                     String sex, String schoolNo, String strCollege,
                     String email, String password, Date dateBirthday, Date createdAt) {
        super(id, firstName, midName, lastName, sex, schoolNo, strCollege,
                email, password, dateBirthday, createdAt);
    }

    UserModel fromSnapshot(DocumentSnapshot snapshot){
        return new UserModel(
                snapshot.getId(),
                snapshot.getString(getFirstName()),
                snapshot.getString(getMidName()),
                snapshot.getString(getLastName()),
                snapshot.getString(getSex()), //TODO: casting might not work
                snapshot.getString(getSchoolNo()),
                snapshot.getString(getCollege()),
                snapshot.getString(getEmail()),
                snapshot.getString(getPassword()),
                snapshot.getDate(getBirthday().toString()),
                snapshot.getDate(getCreatedAt().toString())
                );
    }
    //Interface methods

    @Override
    public String getModelName() {
        return "Users";
    }
    @Override
    public String getObjectName() {
        return this.getFullName();
    }
    @Override
    public String getObjectID() {
        return this.getId();
    }
    @Override
    public Object getObject() {
        return this;
    }
}
