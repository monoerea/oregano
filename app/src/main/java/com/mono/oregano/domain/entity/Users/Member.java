package com.mono.oregano.domain.entity.Users;

import com.google.type.DateTime;

public class Member {
    /**
     * Each user can have 1 member class, a member class can have multiple organizations
     */
    String _MID;
    //Only one
    String _UID;
    //Can have multiple relations
    String _OIDs;

    DateTime createdAt;

}
