package com.mono.oregano.data.dataModel.users;

import java.util.ArrayList;

public class Member extends User {

    private int role;
    //store organizations
    private ArrayList<String> memberOf;

    public Member(String id, int role,  ArrayList<String> memberOf) {
        super(id,memberOf);
        this.role = role;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public ArrayList<String> getMemberOf() {
        return memberOf;
    }

    public void setMemberOf(ArrayList<String> memberOf) {
        this.memberOf = memberOf;
    }


}
