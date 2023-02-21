package com.mono.oregano.data.model;

import com.mono.oregano.domain.entity.OrganizationEntity;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Organization extends OrganizationEntity implements Model{

    public Organization(String id, String name, String desc, String profile, LocalDateTime dateCreated, ArrayList<String> members) {
        super(id, name, desc, profile, dateCreated, members);
    }

    //CRUD
    //POSTS


    @Override
    public HashMap<String, Object> toDocument() {
        return Model.super.toDocument();
    }

    public String getByPID(String id){
        for (int i = 0; i < getPostCount(); i++) {
            if (Objects.equals(id, getPosts().get(i))) {
                return getMembers().get(i);
            }
        }
        return null;
    }
    //ORGANIZATIONS
    public String getByOID(String id){
        for (int i = 0; i < getMemberCount(); i++) {
            if (Objects.equals(id, getMembers().get(i))) {
                return getMembers().get(i);
            }
        }
        return null;
    }
    // Auto set DATA
    public String setStatus(){
        if (this.isActive()){
            return "Active";
        }
        return "Inactive";
    }

    public void setDate() {
        LocalDateTime now = LocalDateTime.now();
        this.setDateCreated(now);
    }

    public boolean _isDeactivated(){
        if (this.isActive()){
            return false;
        }
        return true;
    }

    @Override
    public String getModelName() {
        return null;
    }

    @Override
    public String getObjectName() {
        return null;
    }

    @Override
    public String getObjectID() {
        return null;
    }

    @Override
    public Object getObject() {
        return null;
    }

    public static void main(String args[]) throws IOException {
        ArrayList<String> members = new ArrayList<>();
        members.add("231024");
        Organization organization = new Organization("1", "Org","sample", "something", LocalDateTime.now(), members);
        for (Field field:organization.getClass().getDeclaredFields()
        ) {
            System.out.println(field);
        }
    }
}
