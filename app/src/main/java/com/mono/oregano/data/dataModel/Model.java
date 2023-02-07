package com.mono.oregano.data.dataModel;

import java.util.HashMap;

public interface Model{
    String getModelName();
    String getObjectName();
    String getObjectID();
    HashMap<String, Object> getUpdates() throws IllegalAccessException;
}
