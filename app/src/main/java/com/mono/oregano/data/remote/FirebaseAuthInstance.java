package com.mono.oregano.data.remote;

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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FirebaseAuthInstance implements DataSources {
    private ExecutorService executor = Executors.newSingleThreadExecutor();
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
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener((Executor) this, task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                user = mAuth.getCurrentUser();
            }
            else {
                Log.e("error", task.getException().getMessage());
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
    public Future<Task<AuthResult>> registUser(String email, String password){
        return executor.submit(() ->
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(executor, task -> {
                    if (task.isSuccessful()){
                        user = task.getResult().getUser();
                    }
                }));
    }
    public void registerUser(String strEmail, String strPassword){
        mAuth.createUserWithEmailAndPassword(strEmail, strPassword).addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d( "Success", "createUserWithEmail:success" );
                    FirebaseUser user = mAuth.getCurrentUser();
                } else {
                    Log.w("Failed", "createUserWithEmail:failed");
                }
            }
        });
    }
    public Result<? extends Model> register(String firstName, String midName, String lastName,
                                            String sex, String schoolNo, String college, String email,
                                            String password, String birthday) {
        try {
            //regisUser(email, password);
            registerUser(email, password);
            user = mAuth.getCurrentUser();
            regisUser = new UserModel(user.getUid(), firstName, midName, lastName,sex, schoolNo, college,
                    user.getEmail(), password, birthday, new Date());
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