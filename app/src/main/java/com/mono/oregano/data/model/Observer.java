package com.mono.oregano.data.model;

import java.lang.reflect.Field;
import java.util.HashMap;
@Deprecated
public class Observer {
    protected HashMap<String,Object> hashList;

    protected HashMap<String, Object> onCreationHashCodes(){
        HashMap<String, Object> initial = new HashMap<>();
        for (Field field:this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                initial.put(this.getClass().getField(field.getName()).getName(),this.getClass().getField(field.getName()).get(this));
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return initial;
    }

    public HashMap<String, Object> getUpdated() throws IllegalAccessException {
        //method class to call to update the current model class instance.
        //returns a hashmap of updated values to be used inside the db class.
        HashMap<String, Object> updates = new HashMap<>();
        for (Field field:this.getClass().getDeclaredFields()){
            if (this.hashList.containsValue(field.hashCode()) == false){
                field.setAccessible(true);
                updates.put(field.getName(), field.get(this));
            }
        }
        updateHash(updates);
        return updates;
    }

    private void updateHash(HashMap<String,Object> updates){
        //Class to update the current hashList from newList
        updates.forEach((key, value)->this.hashList.merge(key, value, (oldValue, newValue)->{
            if (oldValue!= newValue){
                return newValue;
            }
            return oldValue;
        }));
    }
}
