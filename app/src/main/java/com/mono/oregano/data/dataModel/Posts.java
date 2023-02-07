package com.mono.oregano.data.dataModel;

import java.util.HashMap;
import java.util.HashSet;

public class Posts extends Observer implements Model{
    private String id;
    private String Name;

    public Posts(String id, String name) {
        this.hashList = onCreationHashCodes();
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

    @Override
    public HashMap<String, Object> getUpdates() throws IllegalAccessException {
        return getUpdated();
    }
}
