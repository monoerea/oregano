package com.mono.oregano.presentation.auth;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mono.oregano.R;
import com.mono.oregano.data.model.Model;
import com.mono.oregano.data.model.users.LoggedInUser;
import com.mono.oregano.data.model.users.baseUser;
import com.mono.oregano.data.repository.user.AuthRepository;
import com.mono.oregano.domain.util.Result;

import java.util.regex.Pattern;

/**
 * Shared view-model class
 * Class that contains the logic for non view related functions
 * such as login, register and validity checks
 * Emits values for observing
 * Follows the singleton and observer pattern
 */
public class AuthViewModel extends ViewModel {
    protected AuthRepository authRepository;

    protected baseUser user;
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
    public void register(String firstName, String midName, String lastName, String gender, String schoolNo,String email, String password) {
        // can be launched in a separate asynchronous job
        Result<? extends Model> result = authRepository.registerLogin(firstName, midName, lastName, gender, schoolNo,email, password);

        if (result instanceof Result.Success) {

            user = ((Result.Success<baseUser>) result).getData();
            authResult.setValue(new AuthResult(new AuthUserView(user.getFullName())));
        } else {
            authResult.setValue(new AuthResult(R.string.register_failed));
        }
    }

    //Emits the observable live data for the view to observe
    public void loginDataChanged(String email, String password) {

        if (!isEmailValid(email)) {
            authFormState.setValue(new AuthFormState(R.string.invalid_email, null));
        } else if (!isPasswordValid(password)) {
            authFormState.setValue(new AuthFormState(null, R.string.invalid_password));
        } else {
            authFormState.setValue(new AuthFormState(true));
        }
    }
    // Similar to above but for Register fragment
    public void regisDataChanged(String firstName,
                                 String midName,
                                 String lastName,
                                 String gender,
                                 String schoolNo,
                                 String email,
                                 String password) {

        if (!isNameValid(firstName)){
            authFormState.setValue(new AuthFormState(R.string.invalid_name, null,
                    null,null,null,null));
        }
        else if (!isNameValid(midName)){
            authFormState.setValue(new AuthFormState(null,R.string.invalid_name,
                    null, null,null,null));
        }
        else if (!isNameValid(lastName)){
            authFormState.setValue(new AuthFormState(null,null,
                    R.string.invalid_name, null, null,null));
        }
        else if (!isNameValid(gender)){
            authFormState.setValue(new AuthFormState(null, null,
                    null,null,null, R.string.invalid_school_num));
        }
        else if (!isSchoolNumValid(schoolNo)){
            authFormState.setValue(new AuthFormState(null, null,
                    null,null,null, R.string.invalid_school_num));
        }
        else if (!isEmailValid(email)) {
            authFormState.setValue(new AuthFormState(null,null,
                    null,R.string.invalid_email, null, null));
        }
        else if (!isPasswordValid(password)) {
            authFormState.setValue(new AuthFormState(null,null,
                    null,null, R.string.invalid_password,null));
        }
        else {
            authFormState.setValue(new AuthFormState(true));
        }
    }

    // A placeholder password validation check

    protected boolean isPasswordValid(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–_[{}]:;',?/*~$^+=<>]).{8,20}$";
        Pattern PASSWORD = Pattern.compile(regex);
        return password != null && PASSWORD.matcher(password).matches();
    }

    protected boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        else {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }
    protected boolean isSchoolNumValid(String schoolNum){
        String regex = "^(?:(?:19|20)[0-9]{2})-([1-9]{5})-([A-Z]{2})-([0-9]{1})";
        Pattern SCHOOL_NUM = Pattern.compile(regex);
        return schoolNum != null && SCHOOL_NUM.matcher(schoolNum).matches();
    }

    protected boolean isNameValid(String name) {
        if (name == null) {
            return false;
        }
        return !name.trim().isEmpty();
    }
}
