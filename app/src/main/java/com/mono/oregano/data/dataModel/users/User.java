package com.mono.oregano.data.dataModel.users;

import com.mono.oregano.data.dataModel.Model;
import com.mono.oregano.data.dataModel.Observer;
import com.mono.oregano.data.dataModel.Posts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class User extends Observer implements Model{
    //ID
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

    // My Collections
    private HashSet<Posts> posts;
    private ArrayList<String> isFollowing;
    private ArrayList<String> memberOf;

    private User(String firstName, String midName, String lastName, String gender, String schoolNo, String email, String password, String s){
        this.firstName = firstName;
        this.midName = midName;
        this.lastName = lastName;
        fullName();

        this.gender = gender;
        this.schoolNo = schoolNo;
        this.email = email;
        this.password = password;
        this.hashList = onCreationHashCodes();
    }

    public User(String id){
        this.id = id;
    }

    public User(String id, ArrayList<String> memberOf) {
        this.id = id;
        this.memberOf = memberOf;
    }

    public User(String id, String email) {
        super();
        this.id = id;
        this.email = email;
    }

    void fullName(){
        this.fullName = this.firstName + " " + this.midName+ " "+ this.lastName;
    }
    void hashPassword(){
        //TODO: implements a password hash storing sometime
    }
    //GETTERS
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

    public HashSet<Posts> getPosts() {
        return posts;
    }

    public void setPosts(HashSet<Posts> posts) {
        this.posts = posts;
    }

    public ArrayList<String> getMembered() {
        return memberOf;
    }

    public void setMembered(ArrayList<String> oIDS) {
        this.memberOf =  oIDS;
    }

    public ArrayList<String> getIsFollowing() {
        return isFollowing;
    }

    public void setIsFollowing(ArrayList<String> isFollowing) {
        this.isFollowing = isFollowing;
    }

    @Override
    public String getModelName() {
        return "User";
    }
    
    @Override
    public String getObjectName() {
        return this.fullName;
    }

    @Override
    public String getObjectID() {
        return this.id;
    }

    @Override
    public HashMap<String, Object> getUpdates() throws IllegalAccessException {
        return getUpdated();
    }
}
