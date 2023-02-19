package com.mono.oregano.data.datasource.model;

import com.google.firebase.firestore.DocumentSnapshot;
import com.mono.oregano.domain.domModel.Entities.OrgEntity;

import java.util.Date;
import java.util.HashMap;

public class OrgModel extends OrgEntity {
    public OrgModel(String oID, String oName, String oDesc, Date oDate) {
        super(oID, oName, oDesc, oDate);
    }

    OrgModel fromSnapshot(DocumentSnapshot snapshot){
        return new OrgModel(snapshot.getString(getId()),snapshot.getString(getName()),
                snapshot.getString(getDesc()), snapshot.getDate(getDateCreated().toString()));
    }
    HashMap<String, Object> toDocument(){
        HashMap<String, Object> org =  new HashMap<>();
        org.put("id",getId());
        org.put("name",getName());
        org.put("desc",getDesc());
        org.put("date",getDateCreated());
        return org;
    }
}
