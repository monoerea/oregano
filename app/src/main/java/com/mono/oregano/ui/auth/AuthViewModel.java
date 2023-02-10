package com.mono.oregano.ui.auth;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mono.oregano.R;
import com.mono.oregano.data.dataModel.users.LoggedInUser;
import com.mono.oregano.data.dataModel.users.baseUser;
import com.mono.oregano.data.repository.Result;
import com.mono.oregano.data.repository.user.AuthRepository;
import com.mono.oregano.ui.auth.login.LoginViewModel;
import com.mono.oregano.ui.auth.register.RegisterViewModel;
import com.mono.oregano.ui.auth.register.RegisteredUserView;

import java.util.regex.Pattern;

public class AuthViewModel extends ViewModel {
    protected AuthRepository authRepository;

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
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            authResult.setValue(new AuthResult(new AuthUserView(data.getFullName())));
        } else {
            authResult.setValue(new AuthResult(R.string.login_failed));
        }
    }

    public void loginDataChanged(String email, String password) {
        if (!isUserNameValid(email)) {
            authFormState.setValue(new AuthFormState(R.string.invalid_email, null, null));
        } else if (!isPasswordValid(password)) {
            authFormState.setValue(new AuthFormState(null, R.string.invalid_password, null));
        } else {
            authFormState.setValue(new AuthFormState(true));
        }
    }

    public void register(String firstName, String midName, String lastName, String gender, String schoolNo,String email, String password) {
        // can be launched in a separate asynchronous job
        Result<baseUser> result = authRepository.registerLogin(firstName, midName, lastName, gender, schoolNo,email, password);

        if (result instanceof Result.Success) {
            baseUser data = ((Result.Success<baseUser>) result).getData();
            authResult.setValue(new AuthResult(new RegisteredUserView(data.getFullName())));
        } else {
            authResult.setValue(new AuthResult(R.string.login_failed));
        }
    }
    public void regisDataChanged(String firstName, String midName, String lastName, String gender, String schoolNo,String email, String password) {

        if (!isNameValid(firstName)){
            authFormState.setValue(new AuthFormState(R.string.invalid_name, null, null));
        }
        if (!isNameValid(midName)){
            authFormState.setValue(new AuthFormState(R.string.invalid_name, null, null));
        }
        if (!isNameValid(lastName)){
            authFormState.setValue(new AuthFormState(R.string.invalid_name, null, null));
        }
        if (!isNameValid(gender)){
            authFormState.setValue(new AuthFormState(R.string.invalid_gender, null, null));
        }
        if (!isEmailValid(email)) {
            authFormState.setValue(new AuthFormState(R.string.invalid_email, null, null));
        }
        if (!isPasswordValid(password)) {
            authFormState.setValue(new AuthFormState(null, R.string.invalid_password,null));
        }
        if (!isSchoolNumValid(schoolNo)){
            authFormState.setValue(new AuthFormState(null, null, R.string.invalid_school_num));
        }
        else {
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

        if (password == null) {
            return false;
        }
        if (password.trim().length() > 15) {
            return PASSWORD.matcher(password).matches();
        }
        return !password.trim().isEmpty();
    }

    protected boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    protected boolean isSchoolNumValid(String schoolNum) {
        //TODO: MATCH THE SCHOOL NUMBER TO REGEX
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";
        Pattern SCHOOL_NUMBER = Pattern.compile(regex);
        if (schoolNum == null) {
            return false;
        }
        if (schoolNum.trim().length() > 15) {
            return SCHOOL_NUMBER.matcher(schoolNum).matches();
        }
        return !schoolNum.trim().isEmpty();
    }

    protected boolean isNameValid(String name) {
        if (name == null) {
            return false;
        }
        return !name.trim().isEmpty();
    }

    private LoginViewModel isLoginInstance(AuthViewModel model) {
        if (model instanceof LoginViewModel) {
            return new LoginViewModel(this.authRepository);
        }
        return null;
    }

    private RegisterViewModel isRegisInstance(AuthViewModel model) {
        if (model instanceof RegisterViewModel) {
            return  new RegisterViewModel(this.authRepository);
        }
        return null;
    }
}
