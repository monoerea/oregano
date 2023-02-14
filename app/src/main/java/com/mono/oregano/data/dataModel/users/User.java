package com.mono.oregano.data.dataModel.users;

import com.mono.oregano.data.dataModel.Model;
import com.mono.oregano.data.dataModel.Posts;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Data class for User
 */
public class User implements Model {
    private String uid;

    // Basic User Info
    private String firstName;
    private String midName;
    private String lastName;
    private String fullName;
    private String gender;
    //Login Info
    private String schoolNo;
    private String email;
    private String password;

    // My Collections
    private HashSet<Posts> posts;
    private ArrayList<String> isFollowing;
    private ArrayList<String> memberOf;

    public User(String id, String firstName, String midName, String lastName, String gender, String schoolNo, String email, String password){
        this.uid = id;
        this.firstName = firstName;
        this.midName = midName;
        this.lastName = lastName;
        setFullName();
        this.gender = gender;
        this.schoolNo = schoolNo;
        this.email = email;
        this.password = password;
        //this.hashList = onCreationHashCodes();
    }
    void setFullName(){
        this.fullName = this.firstName + " " + this.midName+ " "+ this.lastName;
    }

    /*
    * Getters and Setters
    * */


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMidName() {
        return midName;
    }

    public void setMidName(String midName) {
        this.midName = midName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSchoolNo() {
        return schoolNo;
    }

    public void setSchoolNo(String schoolNo) {
        this.schoolNo = schoolNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashSet<Posts> getPosts() {
        return posts;
    }

    public void setPosts(HashSet<Posts> posts) {
        this.posts = posts;
    }

    public ArrayList<String> getIsFollowing() {
        return isFollowing;
    }

    public void setIsFollowing(ArrayList<String> isFollowing) {
        this.isFollowing = isFollowing;
    }

    public ArrayList<String> getMemberOf() {
        return memberOf;
    }

    public void setMemberOf(ArrayList<String> memberOf) {
        this.memberOf = memberOf;
    }

    @Override
    public String getModelName() {
        return "Users";
    }

    @Override
    public String getObjectName() {
        return this.fullName;
    }

    @Override
    public String getObjectID() {
        return this.uid;
    }

    @Override
    public Object getObject() {
        return this;
    }
}
