package com.mono.oregano.ui.auth.login;

import androidx.annotation.StringRes;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.mono.oregano.R;
import com.mono.oregano.data.repository.baseRepository;
import com.mono.oregano.data.repository.user.AuthRepository;
import com.mono.oregano.databinding.FragmentLoginBinding;

import com.mono.oregano.ui.BaseFragment;
import com.mono.oregano.ui.auth.AuthUserView;
import com.mono.oregano.ui.auth.AuthViewModel;
import com.mono.oregano.ui.auth.ViewModelFactory;

public class LoginFragment extends BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository> {

    private AuthViewModel loginViewModel;
    private FragmentLoginBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

      binding = FragmentLoginBinding.inflate(inflater, container, false);
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
        return binding;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginViewModel = new ViewModelProvider(this, new ViewModelFactory(AuthRepository.getInstance()))
                .get(AuthViewModel.class);

        final TextInputLayout usernameEditText = binding.email.getRoot();
        final TextInputLayout passwordEditText = binding.password.getRoot();
        final Button loginButton = binding.login.getRoot();
        final ProgressBar loadingProgressBar = binding.progressBar.getRoot();

        loginViewModel.getAuthState().observe(getViewLifecycleOwner(), loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            loginButton.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getEmailError() != null) {
                usernameEditText.setError(getString(loginFormState.getEmailError()));
            }else{
                usernameEditText.setError(null);
            }
            if (loginFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(loginFormState.getPasswordError()));
            }else{
                passwordEditText.setError(null);
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
                loginViewModel.loginDataChanged(usernameEditText.getEditText().getText().toString(),
                        passwordEditText.getEditText().toString());
            }
        };
        usernameEditText.getEditText().addTextChangedListener(afterTextChangedListener);
        passwordEditText.getEditText().addTextChangedListener(afterTextChangedListener);
        passwordEditText.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getEditText().getText().toString(),
                            passwordEditText.getEditText().getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getEditText().getText().toString(),
                        passwordEditText.getEditText().getText().toString());
            }
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
        super.onDestroyView();
        binding = null;
    }
}