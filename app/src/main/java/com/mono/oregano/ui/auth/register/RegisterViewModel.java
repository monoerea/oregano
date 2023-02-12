package com.mono.oregano.ui.auth.register;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mono.oregano.R;
import com.mono.oregano.data.dataModel.Model;
import com.mono.oregano.data.dataModel.users.LoggedInUser;
import com.mono.oregano.data.dataModel.users.baseUser;
import com.mono.oregano.data.repository.Result;
import com.mono.oregano.data.repository.user.AuthRepository;
import com.mono.oregano.ui.auth.AuthFormState;
import com.mono.oregano.ui.auth.AuthResult;
import com.mono.oregano.ui.auth.AuthViewModel;

import java.util.regex.Pattern;

public class RegisterViewModel extends AuthViewModel{
    private MutableLiveData<AuthFormState> regisFormState = new MutableLiveData<>();
    private MutableLiveData<AuthResult> regisResult = new MutableLiveData<>();
    private AuthRepository authRepository;

    public RegisterViewModel(AuthRepository authRepository) {
        super(authRepository);
        this.authRepository = authRepository;
    }

    LiveData<AuthFormState> getRegisFormState() {
        return regisFormState;
    }
    LiveData<AuthResult> getRegisResult() {
        return regisResult;
    }

    public void register(String firstName, String midName, String lastName, String gender, String schoolNo,String email, String password) {
        // can be launched in a separate asynchronous job
        Result<? extends Model> result = authRepository.registerLogin(firstName, midName, lastName, gender, schoolNo,email, password);

        if (result instanceof Result.Success) {
            baseUser data = ((Result.Success<baseUser>) result).getData();
            regisResult.setValue(new AuthResult(new RegisteredUserView(data.getFullName())));
        } else {
            regisResult.setValue(new AuthResult(R.string.login_failed));
        }
    }
    public void regisDataChanged(String firstName, String midName, String lastName, String gender, String schoolNo,String email, String password) {

        if (!isNameValid(firstName)){
            regisFormState.setValue(new AuthFormState(R.string.invalid_name, null, null));
        }
        if (!isNameValid(midName)){
            regisFormState.setValue(new AuthFormState(R.string.invalid_name, null, null));
        }
        if (!isNameValid(lastName)){
            regisFormState.setValue(new AuthFormState(R.string.invalid_name, null, null));
        }
        if (!isNameValid(gender)){
            regisFormState.setValue(new AuthFormState(R.string.invalid_gender, null, null));
        }
        if (!isEmailValid(email)) {
            regisFormState.setValue(new AuthFormState(R.string.invalid_email, null, null));
        }
        if (!isPasswordValid(password)) {
            regisFormState.setValue(new AuthFormState(null, R.string.invalid_password,null));
        }
        if (!isSchoolNumValid(schoolNo)){
            regisFormState.setValue(new AuthFormState(null, null, R.string.invalid_school_num));
        }
        else {
            regisFormState.setValue(new AuthFormState(true));
        }
    }
}
