package com.mono.oregano.presentation.auth.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.mono.oregano.R;
import com.mono.oregano.data.repository.user.AuthRepository;
import com.mono.oregano.databinding.FragmentLoginBinding;
import com.mono.oregano.domain.util.uiUtil;
import com.mono.oregano.presentation.BaseFragment;
import com.mono.oregano.presentation.auth.AuthUserView;
import com.mono.oregano.presentation.auth.AuthViewModel;
import com.mono.oregano.presentation.auth.ViewModelFactory;

import java.util.Objects;

public class LoginFragment extends BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository> {
    private AuthViewModel loginViewModel;
    private FragmentLoginBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = getFragmentBinding(inflater, container);
        ViewModelFactory factory = new ViewModelFactory(getFragmentRepository());
        vModel = new ViewModelProvider(this, factory).get(getViewModel());
        return binding.getRoot();
    }

    @Override
    protected Class<AuthViewModel> getViewModel() {
        return AuthViewModel.class;
    }
    @Override
    protected AuthRepository getFragmentRepository() {
        return AuthRepository.getInstance();
    }
    @Override
    protected FragmentLoginBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentLoginBinding.inflate(inflater, container, false);
    }

    //Overridden method from Fragments
    //Sets the properties of the view on created
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginViewModel = new ViewModelProvider(this, new ViewModelFactory(AuthRepository.getInstance()))
                .get(AuthViewModel.class);

        final TextInputLayout editTextEmail = binding.email.getRoot();
        final TextInputLayout editTextPassword = binding.password.getRoot();
        final Button btnLogin = binding.login.getRoot();
        final ProgressBar loadingProgressBar = binding.progressBar.getRoot();

        setUpUI();


        //Observes the state emitted from the view model class
        //and changes the component attributes based on the state
        loginViewModel.getAuthState().observe(getViewLifecycleOwner(), loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            btnLogin.setEnabled(loginFormState.isDataValid());

            if (loginFormState.getPasswordError() != null) {
                editTextPassword.setError(getString(loginFormState.getPasswordError()));
            }else{
                editTextPassword.setError(null);
            }
            if (loginFormState.getEmailError() != null) {
                editTextEmail.setError(getString(loginFormState.getEmailError()));
            }else{
                editTextEmail.setError(null);
            }

        });

        //Observe the emitted Auth result form the ViewModel livedata
        //Updates the UI to inform if login was a success or fail
        loginViewModel.getAuthResult().observe(getViewLifecycleOwner(), loginResult -> {
            if (loginResult == null) {
                return;
            }
            loadingProgressBar.setVisibility(View.GONE);
            if (loginResult.getError() != null) {
                showLoginFailed(loginResult.getError());
            }
            if (loginResult.getSuccess() != null) {
                updateUiWithUser(loginResult.getSuccess());
            }
        });
        //Variable used to observe the changes to the input for each character entered
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(Objects.requireNonNull(editTextEmail.getEditText()).getText().toString(),
                        Objects.requireNonNull(editTextPassword.getEditText()).toString());
            }
        };
        Objects.requireNonNull(editTextEmail.getEditText()).addTextChangedListener(afterTextChangedListener);
        Objects.requireNonNull(editTextPassword.getEditText()).addTextChangedListener(afterTextChangedListener);
        editTextPassword.getEditText().setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(editTextEmail.getEditText().getText().toString(),
                        editTextPassword.getEditText().getText().toString());
            }
            return false;
        });
        //Sends the current accepted data from view to string to login
        btnLogin.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);
            loginViewModel.login(editTextEmail.getEditText().getText().toString(),
                    editTextPassword.getEditText().getText().toString());
        });


    }
    //Method that sets the success message for the view and navigates to
    // a new view
    private void setUpUI(){
        binding.headerTitle.getRoot().setText(R.string.action_sign_in);
        binding.createAccount.getRoot().setText(R.string.prompt_redirect);

        //Navigation to redirect to register fragment
        binding.createAccount.getRoot().setOnClickListener(v ->
                uiUtil.navigate(binding.getRoot(), binding.createAccount.getRoot(),
                        R.id.action_loginFragment_to_registerFragment));
    }

    private void updateUiWithUser(AuthUserView model) {
        String welcome = getString(R.string.welcome) + model.getFullName();
        // TODO : initiate successful logged in experience
        if (getContext() != null && getContext().getApplicationContext() != null) {
            Toast.makeText(getContext().getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        }

    }
    //Method that sets the fail message for the view
    // and displays the context
    private void showLoginFailed(@StringRes Integer errorString) {
        if (getContext() != null && getContext().getApplicationContext() != null) {
            Toast.makeText(
                    getContext().getApplicationContext(),
                    errorString,
                    Toast.LENGTH_LONG).show();
        }
    }

@Override
    //Describes what to do the View upon destroy
    // Logs out the user if the button is not checked
    public void onDestroyView() {
        final CheckBox checkBox = binding.checkbox.getRoot();
        loginViewModel.signOut(checkBox.isChecked());
        super.onDestroyView();
        binding = null;
    }
}