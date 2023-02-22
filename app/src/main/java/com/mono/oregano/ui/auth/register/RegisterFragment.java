package com.mono.oregano.ui.auth.register;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.mono.oregano.R;
import com.mono.oregano.data.repository.user.AuthRepository;
import com.mono.oregano.databinding.DropDownEditTextBinding;
import com.mono.oregano.databinding.FragmentRegisterBinding;
import com.mono.oregano.domain.util.uiUtil;
import com.mono.oregano.ui.BaseFragment;
import com.mono.oregano.ui.auth.AuthUserView;
import com.mono.oregano.ui.auth.AuthViewModel;
import com.mono.oregano.ui.auth.ViewModelFactory;

import java.util.Objects;

public class RegisterFragment extends BaseFragment<AuthViewModel, FragmentRegisterBinding, AuthRepository> {

    private AuthViewModel registerViewModel;
    private FragmentRegisterBinding binding;

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
    protected FragmentRegisterBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentRegisterBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerViewModel = new ViewModelProvider(this, new ViewModelFactory(AuthRepository.getInstance()))
                .get(AuthViewModel.class);
        binding.headerTitle.getRoot().setText(R.string.action_sign_up);
        final TextInputLayout fNameEditText = binding.firstName.getRoot();
        final TextInputLayout mNameEditText = binding.midName.getRoot();
        final TextInputLayout lNameEditText = binding.lastName.getRoot();
        final DropDownEditTextBinding sexDropDown = binding.sex;
        final TextInputLayout schoolNumEditText = binding.schoolNum.getRoot();
        final DropDownEditTextBinding collegeDropDown = binding.college;
        final TextInputLayout birthdateEditText = binding.birthdate.getRoot();
        final TextInputLayout emailEditText = binding.email.getRoot();
        final TextInputLayout passwordEditText = binding.password.getRoot();
        final Button registerButton = binding.register.getRoot();
        final Button signIn = binding.loginNow.getRoot();
        final ProgressBar loadingProgressBar = binding.progressBar.getRoot();


        fNameEditText.setHint(R.string.first_name);
        mNameEditText.setHint(R.string.mid_name);
        lNameEditText.setHint(R.string.last_name);
        collegeDropDown.getRoot().setHint(R.string.college);
        birthdateEditText.setHint(R.string.birthdate);
        sexDropDown.getRoot().setHint(R.string.sex);
        schoolNumEditText.setHint(R.string.school_num);

        setSexDropDown(this.getContext(),sexDropDown.autofill);
        setCollegeDropDown(this.getContext(), collegeDropDown.autofill);

        signIn.setText(R.string.already_have_an_acc);
        signIn.setOnClickListener(v -> uiUtil.navigate(binding.getRoot(), binding.loginNow.getRoot(), R.id.action_registerFragment_to_loginFragment));
        registerViewModel.getAuthState().observe(getViewLifecycleOwner(), regisFormState -> {
            if (regisFormState == null) {
                return;
            }
            registerButton.setEnabled(regisFormState.isDataValid());

            if (regisFormState.getFirstNameError()!= null){
                fNameEditText.setError(getString(regisFormState.getFirstNameError()));
            } else {
                fNameEditText.setError(null);
            }
            if (regisFormState.getMidNameError()!= null){
                mNameEditText.setError(getString(regisFormState.getMidNameError()));
            } else {
                mNameEditText.setError(null);
            }
            if (regisFormState.getLastNameError()!= null){
                lNameEditText.setError(getString(regisFormState.getLastNameError()));
            } else {
                lNameEditText.setError(null);
            }
            if (regisFormState.getSchoolNumError()!= null){
                schoolNumEditText.setError(getString(regisFormState.getSchoolNumError()));
            } else {
                schoolNumEditText.setError(null);
            }

            if (regisFormState.getEmailError() != null) {
                emailEditText.setError(getString(regisFormState.getEmailError()));
            } else {
                emailEditText.setError(null);
            }
            if (regisFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(regisFormState.getPasswordError()));
            } else {
                passwordEditText.setError(null);
            }
        });

        registerViewModel.getAuthResult().observe(getViewLifecycleOwner(), regisResult -> {
            if (regisResult == null) {
                return;
            }
            loadingProgressBar.setVisibility(View.GONE);
            if (regisResult.getError() != null) {
                showRegisFailed(regisResult.getError());
            }
            if (regisResult.getSuccess() != null) {
                updateUiWithUser(regisResult.getSuccess());
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
                registerViewModel.regisDataChanged(
                        Objects.requireNonNull(fNameEditText.getEditText()).getText().toString(),
                        Objects.requireNonNull(mNameEditText.getEditText()).getText().toString(),
                        Objects.requireNonNull(lNameEditText.getEditText()).getText().toString(),
                        Objects.requireNonNull(sexDropDown.getRoot().getEditText()).getText().toString(),
                        Objects.requireNonNull(schoolNumEditText.getEditText()).getText().toString(),
                        Objects.requireNonNull(collegeDropDown.getRoot().getEditText()).getText().toString(),
                        Objects.requireNonNull(birthdateEditText.getEditText()).getText().toString(),
                        Objects.requireNonNull(emailEditText.getEditText()).getText().toString(),
                        Objects.requireNonNull(passwordEditText.getEditText()).getText().toString());
            }
        };
        Objects.requireNonNull(emailEditText.getEditText()).addTextChangedListener(afterTextChangedListener);
        Objects.requireNonNull(passwordEditText.getEditText()).addTextChangedListener(afterTextChangedListener);
        passwordEditText.getEditText().setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                registerViewModel.register(
                        Objects.requireNonNull(fNameEditText.getEditText()).getText().toString(),
                        Objects.requireNonNull(mNameEditText.getEditText()).getText().toString(),
                        Objects.requireNonNull(lNameEditText.getEditText()).getText().toString(),
                        Objects.requireNonNull(sexDropDown.getRoot().getEditText()).getText().toString(),
                        Objects.requireNonNull(schoolNumEditText.getEditText()).getText().toString(),
                        Objects.requireNonNull(collegeDropDown.getRoot().getEditText()).getText().toString(),
                        Objects.requireNonNull(birthdateEditText.getEditText()).getText().toString(),
                        emailEditText.getEditText().getText().toString(),
                        passwordEditText.getEditText().toString());
            }
            return false;
        });

        registerButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);
            registerViewModel.register(
                    Objects.requireNonNull(fNameEditText.getEditText()).getText().toString(),
                    Objects.requireNonNull(mNameEditText.getEditText()).getText().toString(),
                    Objects.requireNonNull(lNameEditText.getEditText()).getText().toString(),
                    Objects.requireNonNull(sexDropDown.getRoot().getEditText()).getText().toString(),
                    Objects.requireNonNull(schoolNumEditText.getEditText()).getText().toString(),
                    Objects.requireNonNull(collegeDropDown.getRoot().getEditText()).getText().toString(),
                    Objects.requireNonNull(birthdateEditText.getEditText()).getText().toString(),
                    emailEditText.getEditText().getText().toString(),
                    passwordEditText.getEditText().toString());
        });
    }

    public void setSexDropDown(Context context, AutoCompleteTextView dropdown){
        //TODO: convert to non-static impl
        String[] items = new String[]{"Female", "Male"};
        ArrayAdapter<String> adapterItems = new ArrayAdapter<>(context, R.layout.list_item, items);
        dropdown.setAdapter(adapterItems);
    }

    public void setCollegeDropDown(Context context, AutoCompleteTextView dropdown) {
        String[] items = {"CCIS", "CAF", "CAL", "etc."};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.list_item, items);
        dropdown.setAdapter(adapter);
    }

    private void updateUiWithUser(AuthUserView model) {
        String welcome = getString(R.string.welcome) + model.getFullName();
        // TODO : initiate successful logged in experience
        if (getContext() != null && getContext().getApplicationContext() != null) {
            Toast.makeText(getContext().getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        }
    }

    private void showRegisFailed(@StringRes Integer errorString) {
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
