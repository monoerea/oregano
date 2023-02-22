package com.mono.oregano.data.datasource.remote;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mono.oregano.data.dataModel.Model;
import com.mono.oregano.data.dataModel.users.User;
import com.mono.oregano.data.repository.Result;

import java.io.IOException;
import java.util.ArrayList;


public class FirebaseDBInstance implements DataSources {

    private FirebaseDBInstance instance;
    // Added because of error: java.lang.IllegalStateException: Default FirebaseApp is not initialized in this process com.mono.oregano. Make sure to call FirebaseApp.initializeApp(Context) first.
    // FirebaseApp.initializeApp(this);
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Result<User> searchByID(Model model, String id) {
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
        CollectionReference reference = db.collection(colName).getParent().collection(colName);
        return reference;
    }

    private void add(@Nullable String colRef, Model model) {
        CollectionReference reference = null;
        if (colRef == null){
            reference = db.collection(model.getModelName());
        }
        if (reference == null) {
            reference = createCollection(model.getModelName());
        }
        reference.add(model).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Log.d("Success","Insert success");
            }
        });
    }


    private Result<Model> getByID(Model model, String id) {
        //Returns one queried data
        CollectionReference reference = db.collection(model.getModelName());
        if (reference == null) {
            reference = createCollection(model.getModelName());
        }
        ArrayList<Model> result = new ArrayList<>();
        DocumentReference documentReference;
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
        return new Result.Success<>(result.get(0));
    }

    private Result<ArrayList<Model>> queryByName(Model model, String name) {
        CollectionReference reference;
        ArrayList<Model> queryData = new ArrayList<>();
        db.collection(model.getModelName());
        try {
            reference = db.collection(model.getModelName());
            reference.whereEqualTo("Name", name).limit(100).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    queryData.add(document.toObject(Model.class));
                                }
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
        try {
            reference.document(model.getObjectID()).update(model.getUpdates());
            return new Result.Success(model);
        } catch (IllegalAccessException e) {
            return new Result.Error(new IOException("Document does not exist", e));
        }
    }

    public DataSources getInstance() {
        if (instance == null) {
            return new FirebaseDBInstance();
        }
        return instance;
    }
}

