package com.mono.oregano.domain.domModel.Entities;

import com.mono.oregano.data.dataModel.Posts;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class UserEntity {
    private String id;

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

    private Date createdAt;

    // My Collections
    private HashSet<Posts> posts;
    private ArrayList<String> following;
    private ArrayList<String> memberOf;

    protected UserEntity(String firstName, String midName, String lastName, String gender, String schoolNo, String email, String password, Date date){
        this.firstName = firstName;
        this.midName = midName;
        this.lastName = lastName;
        this.fullName = firstName+ " "+midName+" "+lastName;
        this.gender = gender;
        this.schoolNo = schoolNo;
        this.email = email;
        this.password = password;
        this.createdAt = date;
    }
    protected UserEntity(String firstName, String midName, String lastName, String gender, String schoolNo, String email, String password, Date date, ArrayList<String> following, ArrayList<String> memberOf){
        this.firstName = firstName;
        this.midName = midName;
        this.lastName = lastName;
        this.fullName = firstName+ " "+midName+" "+lastName;
        this.gender = gender;
        this.schoolNo = schoolNo;
        this.email = email;
        this.password = password;
        this.createdAt = date;
        this.following = following;
        this.memberOf = memberOf;
    }

    public UserEntity() {
    }

    /**
     * Getters and Setters
     *
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public HashSet<Posts> getPosts() {
        return posts;
    }

    public void setPosts(HashSet<Posts> posts) {
        this.posts = posts;
    }

    public ArrayList<String> getFollowing() {
        return following;
    }

    public void setFollowing(ArrayList<String> isFollowing) {
        this.following = isFollowing;
    }

    public ArrayList<String> getMemberOf() {
        return memberOf;
    }

    public void setMemberOf(ArrayList<String> memberOf) {
        this.memberOf = memberOf;
    }
}
