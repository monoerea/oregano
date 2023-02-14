package com.mono.oregano.data.dataModel;

public interface Model{
    String getModelName();
    String getObjectName();
    String getObjectID();

    Object getObject();
    //HashMap<String, Object> getUpdates() throws IllegalAccessException;
}
