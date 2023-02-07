package com.mono.oregano.ui.auth;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mono.oregano.R;
import com.mono.oregano.data.dataModel.users.LoggedInUser;
import com.mono.oregano.data.repository.Result;
import com.mono.oregano.data.repository.user.AuthRepository;

import java.util.regex.Pattern;

public class AuthViewModel extends ViewModel {
    protected AuthRepository authRepository;

    protected MutableLiveData<AuthFormState> authFormState = new MutableLiveData<>();

    protected MutableLiveData<AuthResult> authResult = new MutableLiveData<>();

    protected AuthViewModel(AuthRepository authRepository) {this.authRepository = authRepository;}

    LiveData<AuthFormState> getAuthState() {
        return authFormState;
    }

    LiveData<AuthResult> getAuthResult() {
        return authResult;
    }

    public void login(String email, String password) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = authRepository.logIn(email, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            authResult.setValue(new AuthResult(new AuthUserView(data.getFullName())));
        } else {
            authResult.setValue(new AuthResult(R.string.login_failed));
        }
    }
    public void loginDataChanged(String email, String password) {
        if (!isUserNameValid(email)) {
            authFormState.setValue(new AuthFormState(R.string.invalid_email, null,null));
        } else if (!isPasswordValid(password)) {
            authFormState.setValue(new AuthFormState(null, R.string.invalid_password, null));
        } else {
            authFormState.setValue(new AuthFormState(true));
        }
    }
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        //TODO: if username in database return false
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    protected boolean isPasswordValid(String password) {
        //regex for password
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";
        Pattern PASSWORD = Pattern.compile(regex);

        if (password == null){
            return false;
        }
        if (password.trim().length() > 15){
            return PASSWORD.matcher(password).matches();
        }
        return !password.trim().isEmpty();
    }
    protected boolean isEmailValid(String email){
        if (email == null) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    protected boolean isSchoolNumValid(String schoolNum){
        //TODO: MATCH THE SCHOOL NUMBER TO REGEX
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";
        Pattern SCHOOL_NUMBER = Pattern.compile(regex);
        if (schoolNum == null) {
            return false;
        }
        if (schoolNum.trim().length() > 15){
            return SCHOOL_NUMBER.matcher(schoolNum).matches();
        }
        return !schoolNum.trim().isEmpty();
    }
    protected boolean isNameValid(String name){
        if (name == null) {
            return false;
        }
        return !name.trim().isEmpty();
    }

}
