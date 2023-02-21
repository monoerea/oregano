package com.mono.oregano.domain.entity.Posts;

import com.mono.oregano.domain.entity.Users.UserEntity;

public class CommentEntity {

    /**
     * Embedding singular user documents is better so as not to fetch from data multiple times.
     *
     */
    //Default id
    String _CID;
    //Doesn't need the parent data since it is loaded from that.
    String _PID;
    UserEntity author;
    String textContent;

    //changing values
    //Public metrics
    Integer likes;
    Integer dislikes;

    public CommentEntity(String _CID, String _PID, UserEntity author, String textContent) {
        this._CID = _CID;
        this._PID = _PID;
        this.author = author;
        this.textContent = textContent;
    }

    //getters and setters
    public String get_CID() {
        return _CID;
    }

    public void set_CID(String _CID) {
        this._CID = _CID;
    }

    public String get_PID() {
        return _PID;
    }

    public void set_PID(String _PID) {
        this._PID = _PID;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getDislikes() {
        return dislikes;
    }

    public void setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
    }
}
