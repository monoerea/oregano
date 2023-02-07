package com.mono.oregano.ui.auth.register;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.mono.oregano.R;
import com.mono.oregano.data.repository.user.AuthRepository;
import com.mono.oregano.databinding.FragmentRegisterBinding;
import com.mono.oregano.ui.BaseFragment;
import com.mono.oregano.ui.auth.AuthFormState;
import com.mono.oregano.ui.auth.AuthResult;
import com.mono.oregano.ui.auth.ViewModelFactory;

public class RegisterFragment extends BaseFragment<RegisterViewModel, FragmentRegisterBinding, AuthRepository> {

    private RegisterViewModel registerViewModel;
    private FragmentRegisterBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    protected Class<RegisterViewModel> getViewModel() {
        return RegisterViewModel.class;
    }

    @Override
    protected AuthRepository getFragmentRepository() {
        return AuthRepository.getInstance(dataSources);
    }

    @Override
    protected FragmentRegisterBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentRegisterBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerViewModel = new ViewModelProvider(this, new ViewModelFactory())
                .get(RegisterViewModel.class);
        
        final TextInputLayout fNameEditText = binding.firstName.getRoot();
        final TextInputLayout mNameEditText = binding.midName.getRoot();
        final TextInputLayout lNameEditText = binding.lastName.getRoot();
        final TextInputLayout genderDropDown = binding.gender.getRoot();
        final TextInputLayout schoolNumEditText = binding.schoolNum.getRoot();
        final TextInputLayout emailEditText = binding.email.getRoot();
        final TextInputLayout passwordEditText = binding.password.getRoot();
        final Button registerButton = binding.register.getRoot();
        final ProgressBar loadingProgressBar = binding.progressBar.getRoot();

        setUI(this.getContext(),genderDropDown.findViewById(R.id.autofill));

        registerViewModel.getRegisFormState().observe(getViewLifecycleOwner(), new Observer<AuthFormState>() {
            @Override
            public void onChanged(@Nullable AuthFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                registerButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getEmailError() != null) {
                    emailEditText.setError(getString(loginFormState.getEmailError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        registerViewModel.getRegisResult().observe(getViewLifecycleOwner(), new Observer<AuthResult>() {
            @Override
            public void onChanged(@Nullable AuthResult regisResult) {
                if (regisResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (regisResult.getError() != null) {
                    showLoginFailed(regisResult.getError());
                }
                if (regisResult.getSuccess() != null) {
                    updateUiWithUser((RegisteredUserView) regisResult.getSuccess());
                }
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
                //TODO: create the regis fragment to support the other shit
                registerViewModel.regisDataChanged(fNameEditText.getEditText().getText().toString(),
                        mNameEditText.getEditText().getText().toString(),
                        lNameEditText.getEditText().getText().toString(),
                        genderDropDown.getEditText().getText().toString(),
                        schoolNumEditText.getEditText().getText().toString(),
                        emailEditText.getEditText().getText().toString(),
                        passwordEditText.getEditText().toString());
            }
        };
        emailEditText.getEditText().addTextChangedListener(afterTextChangedListener);
        passwordEditText.getEditText().addTextChangedListener(afterTextChangedListener);
        passwordEditText.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    registerViewModel.register(fNameEditText.getEditText().getText().toString(),
                            mNameEditText.getEditText().getText().toString(),
                            lNameEditText.getEditText().getText().toString(),
                            genderDropDown.getEditText().getText().toString(),
                            schoolNumEditText.getEditText().getText().toString(),
                            emailEditText.getEditText().getText().toString(),
                            passwordEditText.getEditText().toString());
                }
                return false;
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                registerViewModel.register(fNameEditText.getEditText().getText().toString(),
                        mNameEditText.getEditText().getText().toString(),
                        lNameEditText.getEditText().getText().toString(),
                        genderDropDown.getEditText().getText().toString(),
                        schoolNumEditText.getEditText().getText().toString(),
                        emailEditText.getEditText().getText().toString(),
                        passwordEditText.getEditText().toString());
            }
        });
    }

    public void setUI(Context context, AutoCompleteTextView dropdown){
        ArrayAdapter<String> adapterItems;
        //TODO: convert to non-static impl
        String[] items = {"Agender", "Cisgender", "Genderfluid","Nonbinary","Transgender"};

        adapterItems = new ArrayAdapter<String>(context, R.layout.list_item, items);
        dropdown.setAdapter(adapterItems);
    }

    private void updateUiWithUser(RegisteredUserView model) {
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
