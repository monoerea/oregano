package com.mono.oregano.ui.auth.login;

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
import com.mono.oregano.ui.BaseFragment;
import com.mono.oregano.ui.auth.AuthUserView;
import com.mono.oregano.ui.auth.AuthViewModel;
import com.mono.oregano.ui.auth.ViewModelFactory;

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
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginViewModel = new ViewModelProvider(this, new ViewModelFactory(AuthRepository.getInstance()))
                .get(AuthViewModel.class);

        final TextInputLayout emailRoot = binding.email.getRoot();
        final TextInputLayout passwordEditText = binding.password.getRoot();
        final Button loginButton = binding.login.getRoot();
        final ProgressBar loadingProgressBar = binding.progressBar.getRoot();
        final Button signUp = binding.createAccount.getRoot();

        binding.headerTitle.getRoot().setText(R.string.action_sign_in);
        signUp.setText(R.string.prompt_redirect);

        signUp.setOnClickListener(v -> uiUtil.navigate(binding.getRoot(), binding.createAccount.getRoot(), R.id.action_loginFragment_to_registerFragment));

        loginViewModel.getAuthState().observe(getViewLifecycleOwner(), loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            loginButton.setEnabled(loginFormState.isDataValid());

            if (loginFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(loginFormState.getPasswordError()));
            }else{
                passwordEditText.setError(null);
            }
            if (loginFormState.getEmailError() != null) {
                emailRoot.setError(getString(loginFormState.getEmailError()));
            }else{
                emailRoot.setError(null);
            }

        });

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
                loginViewModel.loginDataChanged(
                        Objects.requireNonNull(emailRoot.getEditText()).getText().toString(),
                        Objects.requireNonNull(passwordEditText.getEditText()).toString());
            }
        };
        Objects.requireNonNull(emailRoot.getEditText()).addTextChangedListener(afterTextChangedListener);
        Objects.requireNonNull(passwordEditText.getEditText()).addTextChangedListener(afterTextChangedListener);
        passwordEditText.getEditText().setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(emailRoot.getEditText().getText().toString(),
                        passwordEditText.getEditText().getText().toString());
            }
            return false;
        });

        loginButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);
            loginViewModel.login(emailRoot.getEditText().getText().toString(),
                    passwordEditText.getEditText().getText().toString());
        });


    }

    private void updateUiWithUser(AuthUserView model) {
        String welcome = getString(R.string.welcome) + model.getFullName();
        // TODO : initiate successful logged in experience
        if (getContext() != null && getContext().getApplicationContext() != null) {
            Toast.makeText(getContext().getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        }

    }

    private void showLoginFailed(@StringRes Integer errorString) {
        if (getContext() != null && getContext().getApplicationContext() != null) {
            Toast.makeText(
                    getContext().getApplicationContext(),
                    errorString,
                    Toast.LENGTH_LONG).show();
        }
    }

@Override
    public void onDestroyView() {
        final CheckBox checkBox = binding.checkbox.getRoot();
        loginViewModel.signOut(checkBox.isChecked());
        super.onDestroyView();
        binding = null;
    }
}