package com.mono.oregano.ui.auth.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.mono.oregano.data.repository.Result;
import com.mono.oregano.data.dataModel.users.LoggedInUser;
import com.mono.oregano.R;
import com.mono.oregano.data.repository.user.AuthRepository;
import com.mono.oregano.ui.auth.AuthFormState;
import com.mono.oregano.ui.auth.AuthResult;
import com.mono.oregano.ui.auth.AuthViewModel;

public class LoginViewModel extends AuthViewModel {

    private MutableLiveData<AuthFormState> loginFormState = authFormState;
    private MutableLiveData<AuthResult> loginResult = authResult;

    LoginViewModel(AuthRepository authRepository) {
        super(authRepository);
    }

    LiveData<AuthFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<AuthResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = authRepository.logIn(username, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new AuthResult(new LoggedInUserView(data.getFullName())));
        } else {
            loginResult.setValue(new AuthResult(R.string.login_failed));
        }
    }
    public void loginDataChanged(String email, String password) {
        if (!isEmailValid(email)) {
            loginFormState.setValue(new AuthFormState(R.string.invalid_email, null, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new AuthFormState(null, R.string.invalid_password, null));
        } else {
            loginFormState.setValue(new AuthFormState(true));
        }
    }
}