package com.mono.oregano.domain.entity.Posts;

import com.mono.oregano.domain.entity.Users.UserEntity;

import java.util.Date;

public class PostEntity {
    private String _PID;
    private String title;

    private String textContent;
    private UserEntity author;
    private Date dateCreated;
    private String img;

    public PostEntity(String id, String title,String textContent, UserEntity author, Date dateCreated, String img) {
        this._PID = id;
        this.title= title;
        this.textContent = textContent;
        this.author = author;
        this.dateCreated = dateCreated;
        this.img = img;
    }

    public String getId() {
        return _PID;
    }

    public void setId(String id) {
        this._PID = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
