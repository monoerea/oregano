package com.mono.oregano.ui.auth.register;

import androidx.annotation.Nullable;

import com.mono.oregano.ui.auth.AuthFormState;

public class RegisterFormState extends AuthFormState{

    RegisterFormState(@Nullable Integer emailError, @Nullable Integer passwordError) {
        super(emailError, passwordError);
        this.isDataValid = false;
    }

    RegisterFormState(boolean isDataValid) {
        this.emailError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }
}
