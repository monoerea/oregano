package com.mono.oregano.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class OrganizationEntity {
    private String _OID;
    private String oName;
    private String oDesc;

    private String oProfileImg;

    //stores id references to the documents
    private ArrayList<String> members;
    private ArrayList<String> admins;
    private ArrayList<String> posts;

    //cached data
    private int memberCount;
    private int adminCount;
    private int modCount;
    private int postCount;

    //booleans or statuses
    private String status;
    private boolean isAccredited;
    private boolean isActive;
    private boolean isDeactivated;

    private LocalDateTime dateCreated;

    public OrganizationEntity(String id, String name, String desc, String profile, LocalDateTime dateCreated, ArrayList<String> members) {
        this._OID = id;
        this.oName = name;
        this.oDesc = desc;
        this.oProfileImg = profile;
        this.dateCreated = dateCreated;
        this.members = members;
        this.adminCount =1;
        this.isActive = true;
        this.memberCount = members.size();
    }

    /**
     * Getters and Setters
     *
     */

    public String get_OID() {
        return _OID;
    }

    public void set_OID(String _OID) {
        this._OID = _OID;
    }

    public String getoName() {
        return oName;
    }

    public void setoName(String oName) {
        this.oName = oName;
    }

    public String getoDesc() {
        return oDesc;
    }

    public void setoDesc(String oDesc) {
        this.oDesc = oDesc;
    }

    public String getoProfileImg() {
        return oProfileImg;
    }

    public void setoProfileImg(String oProfileImg) {
        this.oProfileImg = oProfileImg;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    public ArrayList<String> getAdmins() {
        return admins;
    }

    public void setAdmins(ArrayList<String> admins) {
        this.admins = admins;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }
}
