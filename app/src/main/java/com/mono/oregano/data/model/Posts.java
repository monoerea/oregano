package com.mono.oregano.data.model;

import com.mono.oregano.domain.entity.Posts.PostEntity;
import com.mono.oregano.domain.entity.Users.UserEntity;

import java.util.Date;
import java.util.HashMap;

public class Posts extends PostEntity implements Model{


    public Posts(String id, String title, String textContent, UserEntity author, Date dateCreated, String img) {
        super(id, title, textContent, author, dateCreated, img);
    }

    @Override
    public HashMap<String, Object> toDocument() {
        return Model.super.toDocument();
    }

    @Override
    public String getModelName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String getObjectName() {
        return this.getTitle();
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
