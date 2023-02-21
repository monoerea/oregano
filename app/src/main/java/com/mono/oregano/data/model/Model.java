package com.mono.oregano.data.model;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Custom interface class to pass to the database as type,
 * extracts the necessary information from the classes
 *
 */

public interface Model{

    // Default implementation of turning the model classes into Hashmaps to be inserted to the database
    default HashMap<String, Object> toDocument(){
        HashMap<String, Object> map = new HashMap<>();
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
            map.put(field.getName(), value);
        }
        return map;
    }
    String getModelName();
    String getObjectName();
    String getObjectID();

    Object getObject();
    //HashMap<String, Object> getUpdates() throws IllegalAccessException;
}
