package com.mono.oregano.data.network;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;
import com.mono.oregano.data.dataModel.Model;
import com.mono.oregano.data.repository.Result;

import java.io.IOException;
import java.util.ArrayList;


public class FirebaseDBInstance implements DataSources{

    private static FirebaseDBInstance instance;

        private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Result<Document> searchByID(Model model, String id) {
        try {
            // Attempts signing using the given
            getByID(model, id);
            // TODO: handle loggedInUser authentication
            return new Result.Success<>(getByID(model, id));
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in, User does not exist", e));
        }
    }

    public Result<Model> insert(Model model) {
        try {
            // Attempts signing using the given
            add(model);
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


    public CollectionReference createCollection(String colName){
        CollectionReference reference = db.collection(colName).getParent().collection(colName);
        return reference;
    }

    public void add(Model model){
        CollectionReference reference = db.collection(model.getModelName());
        if ( reference == null){
            reference = createCollection(model.getModelName());
        }
        reference.add(model);
    }


    public Result<Model> getByID(Model model,String id) {
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
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        result.add(document.toObject(Model.class));
                    }
                }
            }
        });
        return new Result.Success<>(result.get(0));
    }
    public Result<ArrayList<Model>> queryByName(Model model, String name){
        CollectionReference reference;
        ArrayList<Model> queryData = new ArrayList<>();
        if (db.collection(model.getModelName()) == null){
            createCollection(model.getModelName());
        }
        try {
            reference = db.collection(model.getModelName());
            reference.whereEqualTo("Name", name).limit(100).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    queryData.add(document.toObject(Model.class));
                                }
                            }
                        }
                    });
            return new Result.Success<>(queryData);
        }catch (Exception e){
            return new Result.Error(new IOException("Error logging in, User does not exist", e));
        }
    }

    public Result<String> delete(String colRef, String colName,String id){
        CollectionReference reference = db.collection(colRef);
        if ( reference == null){
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

    public Result<String> update(Model model){
        CollectionReference reference = db.collection(model.getModelName());
        if ( reference == null){
            reference = createCollection(model.getModelName());
        }
        try {
            reference.document(model.getObjectID()).update(model.getUpdates());
            return new Result.Success(model);
        } catch (IllegalAccessException e) {
            return new Result.Error(new IOException("Document does not exist", e));
        }
    }

    @Override
    public DataSources getInstance(DataSources sources) {
        return null;
    }

    @Override
    public FirebaseDBInstance getInstance(FirebaseDBInstance source) {
        if (instance == null){
            return new FirebaseDBInstance();
        }
        return instance;
    }

    @Override
    public FirebaseAuthInstance getInstance(FirebaseAuthInstance sources) {
        return null;
    }
}

