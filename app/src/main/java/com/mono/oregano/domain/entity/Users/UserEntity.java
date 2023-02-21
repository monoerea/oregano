package com.mono.oregano.domain.entity.Users;

import com.mono.oregano.data.model.Posts;
import com.mono.oregano.domain.util.Sex;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class UserEntity {
    //Provided by Auth Lib of Firebase
    private String id;

    // Basic User Info
    private String strFirstName;
    private String strMidName;
    private String strLastName;
    private String strFullName;
    private String strGender;
    private Sex biSex;

    private String strCollege;
    private String schoolNo;
    private String email;
    private String password;
    private Date createdAt;

    private Date dateBirthday;
    private String profileImg;

    // My Collections
    //cached data for less read and write to db
    private Integer intOrgFollowingsCount;
    private Integer intOrgMembersCount;
    boolean boolIsVisible;

    // Lists of ids of relationships for Profile Reference
    private HashSet<Posts> posts;
    private ArrayList<String> isFollowing;
    private ArrayList<String> memberOf;

    //empty constructor
    public UserEntity(){}

    public UserEntity(String id, String firstName, String midName,
                      String lastName, String gender, Sex sex, String schoolNo,String strCollege,
                      String email, String password,Date dateBirthday, Date createdAt){
        this.id = id;
        this.strFirstName = firstName;
        this.strMidName = midName;
        this.strLastName = lastName;
        this.strGender = gender;
        this.biSex = sex;
        this.schoolNo = schoolNo;
        this.strCollege = strCollege;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.dateBirthday = dateBirthday;
    }
    /**
     * Getters and Setters
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return strFirstName;
    }

    public void setFirstName(String firstName) {
        this.strFirstName = firstName;
    }

    public String getMidName() {
        return strMidName;
    }

    public void setMidName(String midName) {
        this.strMidName = midName;
    }

    public String getLastName() {
        return strLastName;
    }

    public void setLastName(String lastName) {
        this.strLastName = lastName;
    }

    public String getFullName() {
        return strFullName;
    }

    public void setFullName(String fullName) {
        this.strFullName = fullName;
    }

    public String getGender() {
        return strGender;
    }

    public void setGender(String gender) {
        this.strGender = gender;
    }

    public Sex getSex(){
        return  this.biSex;
    }
    public void setSex(Sex sex) {
        this.biSex = sex;
    }

    public String getSchoolNo() {
        return schoolNo;
    }

    public void setSchoolNo(String schoolNo) {
        this.schoolNo = schoolNo;
    }

    public String getCollege(){return strCollege;}

    public void setCollege(String college){ this.strCollege = college; }

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

    public Date getBirthday() {
        return dateBirthday;
    }

    public void setBirthday(Date birthday) {
        this.dateBirthday = birthday;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
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
}
