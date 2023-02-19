package com.mono.oregano.domain.domModel.Entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class OrgEntity {
    private String id;
    private String Name;
    private String Desc;

    private String profile;

    private ArrayList<String> members;
    private ArrayList<String> posts;

    //cached data
    private int memberCount;
    private int adminCount;
    private int modCount;
    private int postCount;

    //booleans
    private boolean isAccredited;
    private boolean isActive;
    private boolean isDeactivated;

    private Date dateCreated;

    public OrgEntity(String oID, String oName, String oDesc, Date oDate) {
        this.id = oID;
        this.Name = oName;
        this.Desc = oDesc;
        this.dateCreated = oDate;
        //Setting initial values
        this.isAccredited = false;
        this.isActive = true;
        this.isDeactivated = false;
        this.memberCount = 1;
    }
    /**
     * Getters and setters
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    public ArrayList<String> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<String> posts) {
        this.posts = posts;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public int getAdminCount() {
        return adminCount;
    }

    public void setAdminCount(int adminCount) {
        this.adminCount = adminCount;
    }

    public int getModCount() {
        return modCount;
    }

    public void setModCount(int modCount) {
        this.modCount = modCount;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public boolean isAccredited() {
        return isAccredited;
    }

    public void setAccredited(boolean accredited) {
        isAccredited = accredited;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isDeactivated() {
        return isDeactivated;
    }

    public void setDeactivated(boolean deactivated) {
        isDeactivated = deactivated;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
