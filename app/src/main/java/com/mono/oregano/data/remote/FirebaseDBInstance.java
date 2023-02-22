package com.mono.oregano.data.remote;

import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;
import com.mono.oregano.data.model.Model;
import com.mono.oregano.domain.util.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


public class FirebaseDBInstance implements DataSources {

    private static FirebaseDBInstance instance;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Result<? extends Model> searchByID(Model model, String id) {
        try {
            // Attempts signing using the given
            getByID(model, id);
            // TODO: handle loggedInUser authentication
            return new Result.Success<>(getByID(model, id));
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in, User does not exist", e));
        }
    }

    public Result<Model> insert(@Nullable String colRef , Model model) {
        try {
            // Attempts signing using the given
            /**new Thread(new Runnable() {
                @Override
                public void run() {
                    add(colRef,model);
                }
            });
            **/
            add(colRef,model);
            // TODO: handle loggedInUser authentication
            return new Result.Success(model);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in, User does not exist", e));
        }
    }

    public Result<ArrayList<Model>> getByName(Model model, String name) {
        try {
            return new Result.Success<>(queryByName(model, name));
        } catch (Exception e) {
            return new Result.Error(new IOException("Error getting documents.", e));
        }
    }


    public CollectionReference createCollection(String colName) {
        return db.collection(colName).getParent().collection(colName);
    }

    private void add(@Nullable String colRef, Model model) {
        CollectionReference reference = null;
        if (colRef == null){
            reference = db.collection(model.getModelName());
        }
        if (reference == null) {
            reference = createCollection(model.getModelName());
        }
        reference.document(model.getObjectID()).set(model.toDocument(), SetOptions.merge()).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Log.d("Success","Insert success");
            }else{
                Log.w("Error", "Error writing document");
            }
        });
    }


    private Result<Model> getByID(Model model, String id) {
        //Returns one queried data
        CollectionReference reference = db.collection(model.getModelName());
        ArrayList<Model> result = new ArrayList<>();
        DocumentReference documentReference;
        if (reference == null) {
            reference = createCollection(model.getModelName());
        }
        try {
            documentReference = reference.document(id);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error getting documents.", e));
        }
        documentReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    result.add(document.toObject(Model.class));
                }
            }
        });
        if (result!=null){
            return new Result.Success<>(result.get(0));
        }
        return new Result.Error(new IOException("Error getting documents."));
    }

    private Result<ArrayList<Model>> queryByName(Model model, String name) {
        CollectionReference reference;
        ArrayList<Model> queryData = new ArrayList<>();
        if (db.collection(model.getModelName()) == null) {
            createCollection(model.getModelName());
        }
        try {
            reference = db.collection(model.getModelName());
            reference.whereEqualTo("Name", name).limit(100).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                queryData.add(document.toObject(Model.class));
                            }
                        }
                    });
            return new Result.Success<>(queryData);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in, User does not exist", e));
        }
    }

    private Result<String> delete(String colRef, String colName, String id) {
        CollectionReference reference = db.collection(colRef);
        if (reference == null) {
            reference = createCollection(colName);
        }
        try {
            reference.document(id).delete();
            // TODO: handle loggedInUser authentication
            return new Result.Success<>("Document is deleted");
        } catch (Exception e) {
            return new Result.Error(new IOException("Document does not exist", e));
        }
    }

    private Result<String> update(Model model) {
        CollectionReference reference = db.collection(model.getModelName());
        if (reference == null) {
            reference = createCollection(model.getModelName());
        }
        reference.document(model.getObjectID()).update((Map<String, Object>) model.getObject());
        return new Result.Success(model);
    }

    public DataSources getInstance() {
        if (instance == null) {
            return new FirebaseDBInstance();
        }
        return instance;
    }
}

