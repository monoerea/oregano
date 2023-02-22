package com.mono.oregano.data.datasource.remote;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mono.oregano.data.dataModel.users.LoggedInUser;
import com.mono.oregano.data.dataModel.users.User;
import com.mono.oregano.data.repository.Result;

import java.io.IOException;
import java.util.concurrent.Executor;

public class FirebaseAuthInstance implements DataSources {

    private static final String TAG = "FirebaseAuthInstance";
    private FirebaseAuthInstance instance;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private LoggedInUser loggedUser;
    private User regisUser;
    public Result<LoggedInUser> login(String email, String password) {

        try {
            // Attempts signing using the given
            if (mAuth.getCurrentUser()!= null){
                FirebaseUser user = mAuth.getCurrentUser();
                loggedUser = new LoggedInUser(user.getUid(), user.getEmail());
                return new Result.Success<>(loggedUser);
            }else{
                signIn(email, password);
            }
            return new Result.Success<>(loggedUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in, User does not exist", e));
        }
    }

    public Result<String> logOut() {

        try {
            // Attempts signing using the given
            // TODO: handle loggedInUser destroy
            return new Result.Success<>(logout());
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in, User does not exist", e));
        }
    }
    public void signIn(String email,String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener((Executor) this, task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                FirebaseUser user = mAuth.getCurrentUser();
                if (user!= null){
                    String uid = user.getUid();
                    String email1 = user.getEmail();
                    loggedUser = new LoggedInUser(uid, email1);
                }
            }
            else {
                Log.e("error", task.getException().getMessage());
            }
        });
    }

    public void registerUser(String firstName, String midName, String lastName, String sex, String college, String birthdate, String schoolNo, String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((Executor) this, task -> {
            if (task.isSuccessful()){
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null)
                    regisUser = new User(user.getUid());
                else
                    Log.e(TAG, "mAuth has returned null");
                //TODO: create a Document in Firebase FireStore that adds extra info
            }
        });
    }

    public Result<LoggedInUser> getLoggedUser(){
        FirebaseUser user = mAuth.getCurrentUser();
        LoggedInUser logged = new LoggedInUser(
                user != null ? user.getUid() : null,
                user != null ? user.getEmail() : null);
        return new Result.Success<>(logged);
    }

    public String logout() {
        loggedUser = null;
        mAuth.signOut();
        return "Logout Success";
    }

    public Result<User> register(String firstName, String midName, String lastName, String sex, String schoolNo, String college, String birthdate, String email, String password) {
        try {
            registerUser(firstName,midName,lastName,sex,schoolNo,college,birthdate,email, password);
            return new Result.Success<>(regisUser);
        }
        catch (Exception e){
            return new Result.Error(new IOException("Error registering, User does not exist", e));
        }
    }


    @Override
    public DataSources getInstance() {
        if (instance == null){
            return new FirebaseAuthInstance();
        }
        return instance;
    }

}
