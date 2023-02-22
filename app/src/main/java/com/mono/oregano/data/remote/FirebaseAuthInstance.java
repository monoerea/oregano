package com.mono.oregano.data.remote;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mono.oregano.data.model.Model;
import com.mono.oregano.data.model.UserModel;
import com.mono.oregano.data.model.users.LoggedInUser;
import com.mono.oregano.domain.util.Result;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Executor;

public class FirebaseAuthInstance implements DataSources {

    private FirebaseAuthInstance instance;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user;
    private LoggedInUser loggedUser;
    private UserModel regisUser;
    public Result<LoggedInUser> login(String email, String password) {

        try {
            // Attempts signing using the given
            if (mAuth.getCurrentUser()!= null){
                user = mAuth.getCurrentUser();
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
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user!= null){
                        String uid = user.getUid();
                        String email = user.getEmail();
                        loggedUser = new LoggedInUser(uid, email);
                    }
                }
                else {
                    Log.e("error", task.getException().getMessage());
                }
            }
        });
    }

    public void registerUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d(TAG, "createUserWithEmail:success");
                    //TODO: create a Document in Firebase FireStore that adds extra info
                }
            }
        });

    }

    public Result<LoggedInUser> getLoggedUser(){
        FirebaseUser user = mAuth.getCurrentUser();
        LoggedInUser logged = new LoggedInUser(user.getUid(),user.getEmail());
        return new Result.Success<>(logged);
    }

    public String logout() {
        loggedUser = null;
        mAuth.signOut();
        return "Logout Success";
    }

    public Result<? extends Model> register(String firstName, String midName, String lastName,
                                            String sex, String schoolNo, String college, String email,
                                            String password, Date birthday) {
        try {

            new Thread(new Runnable() {
                @Override
                public void run() {
            registerUser(email, password);
                }
            });
            user = mAuth.getCurrentUser();
            if (user == null){
                signIn(email,password);

            }
            String id = user.getUid();
            regisUser = new UserModel(id, firstName, midName, lastName,sex, schoolNo, college, email, password, birthday, new Date());
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
