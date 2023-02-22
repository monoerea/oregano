package com.mono.oregano.ui.auth;

import android.util.Log;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mono.oregano.R;
import com.mono.oregano.data.dataModel.Model;
import com.mono.oregano.data.dataModel.users.LoggedInUser;
import com.mono.oregano.data.dataModel.users.User;
import com.mono.oregano.data.repository.Result;
import com.mono.oregano.data.repository.user.AuthRepository;

public class AuthViewModel extends ViewModel {
    private static final String TAG = "AuthViewModel";
    protected AuthRepository authRepository;


    protected LoggedInUser user;
    protected MutableLiveData<AuthFormState> authFormState = new MutableLiveData<>();

    protected MutableLiveData<AuthResult> authResult = new MutableLiveData<>();

    protected AuthViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public LiveData<AuthFormState> getAuthState() {
        return authFormState;
    }

    public LiveData<AuthResult> getAuthResult() {
        return authResult;
    }

    public void login(String email, String password) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = authRepository.logIn(email, password);

        if (result instanceof Result.Success) {
            user = ((Result.Success<LoggedInUser>) result).getData();
            authResult.setValue(new AuthResult(new AuthUserView(user.getEmail())));
        } else {
            authResult.setValue(new AuthResult(R.string.login_failed));
        }
    }
    public void signOut(boolean checked){
        if (checked){
            return;
        }
        user = null;
        authRepository.signOut();
    }
    public void loginDataChanged(String email, String password) {

        if (!isEmailValid(email)) {
            authFormState.setValue(new AuthFormState(R.string.invalid_email, null));
        } else if (!isPasswordValid(password)) {
            authFormState.setValue(new AuthFormState(null, R.string.invalid_password));
        } else {
            authFormState.setValue(new AuthFormState(true));
        }
    }

    public void register(String firstName, String midName, String lastName, String sex, String schoolNo, String college, String birthdate, String email, String password) {
        // can be launched in a separate asynchronous job
        Result<? extends Model> result = authRepository.registerLogin(firstName, midName, lastName, sex, college, birthdate, schoolNo,email, password);

        if (result instanceof Result.Success) {
            user = (LoggedInUser) ((Result.Success<User>) result).getData();
            authResult.setValue(new AuthResult(new AuthUserView(user.getFullName())));
        } else {
            authResult.setValue(new AuthResult(R.string.login_failed));
        }
    }
    public void regisDataChanged(String firstName,
                                 String midName,
                                 String lastName,
                                 String sex,
                                 String schoolNo,
                                 String college,
                                 String birthdate,
                                 String email,
                                 String password) {

        if (!isNameValid(firstName)){
            authFormState.setValue(new AuthFormState(R.string.invalid_name, null, null,null,null,null));
        }
        if (!isNameValid(midName)){
            authFormState.setValue(new AuthFormState(null,R.string.invalid_name, null, null,null,null));
        }
        if (!isNameValid(lastName)){
            authFormState.setValue(new AuthFormState(null,null,R.string.invalid_name, null, null,null));
        }
        if (!isSchoolNumValid(schoolNo)){
            authFormState.setValue(new AuthFormState(null, null,null,null,null, R.string.invalid_school_num));
        }
        if (!isEmailValid(email)) {
            authFormState.setValue(new AuthFormState(null,null,null,R.string.invalid_email, null, null));
        }
        if (!isPasswordValid(password)) {
            authFormState.setValue(new AuthFormState(null,null,null,null, R.string.invalid_password,null));
        }
        if(
                isNameValid(firstName) &&
                isNameValid(midName) &&
                isNameValid(lastName) &&
                isSchoolNumValid(schoolNo) &&
                isEmailValid(email) &&
                isPasswordValid(password)
        ) {
            authFormState.setValue(new AuthFormState(true));
        } else {
            Log.e(TAG, "MutableLiveData authFormState has not been set. Validations have not been met");
        }
    }

    // A placeholder password validation check
    protected boolean isPasswordValid(String password) {
        return password != null && password.trim().length() >= 8;
    }

    protected boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        else {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    protected boolean isSchoolNumValid(String schoolNum) {
        //TODO: MATCH THE SCHOOL NUMBER TO REGEX
        return schoolNum != null && schoolNum.trim().length() >= 15;
    }

    protected boolean isNameValid(String name) {
        if (name == null) {
            return false;
        }
        return !name.trim().isEmpty();
    }
}
