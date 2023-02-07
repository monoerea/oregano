package com.mono.oregano.data.dataModel;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Organization extends Observer implements Model{
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
    private String status;
    private boolean isAccredited;
    private boolean isActive;
    private boolean isDeactivated;

    private String dateCreated;
    private HashMap<String, Object> data ;

    public Organization(String oID, String oName, String oDesc) {
        this.id = oID;
        this.Name = oName;
        this.Desc = oDesc;
        setDate();
        //Setting initial values
        this.isAccredited = false;
        this.isActive = true;
        this.isDeactivated = _isDeactivated();
        this.memberCount = 1;
        setStatus();
        this.hashList = onCreationHashCodes();
        setData();
    }
    //CRUD
    //POSTS
    private void setData(){
        Field[] allFields = this.getClass().getDeclaredFields();
        for (Field field: allFields){
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            Class<?> targetType = field.getType();
            Object objValue = null;
            try {
                objValue = targetType.newInstance();
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            }
            Object value = null;
            try {
                value = field.get(objValue);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            this.data.put(field.getName(), value);
        }
    }

    public String getByPID(String id){
        for (int i = 0; i < postCount; i++) {
            if (Objects.equals(id, posts.get(i))) {
                return members.get(i);
            }
        }
        return null;
    }
    //ORGANIZATIONS
    public String getByOID(String id){
        for (int i = 0; i < memberCount; i++) {
            if (Objects.equals(id, members.get(i))) {
                return members.get(i);
            }
        }
        return null;
    }
    // Auto set DATA
    public String setStatus(){
        if (this.isActive){
            return "Active";
        }
        return "Inactive";
    }

    public void setDate() {
        LocalDateTime now = LocalDateTime.now();
        this.dateCreated = String.valueOf(now);
    }

    public boolean _isDeactivated(){
        if (this.isActive){
            return false;
        }
        return true;
    }


    //GETTERS AND SETTERS

    public String getID() {
        return id;
    }

    public void setID(String oID) {
        this.id = oID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String oName) {
        this.Name = oName;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String oDesc) {
        this.Desc = oDesc;
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

    public String getDateCreated() {
        return dateCreated;
    }


    @Override
    public String getModelName() {
        return this.getClass().getSimpleName();
    }
    @Override
    public String getObjectName() {
        return this.Name;
    }

    @Override
    public String getObjectID() {
        return this.id;
    }


    public HashMap<String, Object> getUpdates() throws IllegalAccessException {
        return getUpdated();
    }

    public static void main(String args[]) throws IOException {
        Organization organization = new Organization("1", "Org","sample");
        for (Field field:organization.getClass().getDeclaredFields()
             ) {
            System.out.println(field);
        }
    }
}
