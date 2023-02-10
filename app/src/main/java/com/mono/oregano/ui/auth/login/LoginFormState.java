package com.mono.oregano.ui.auth.login;

import androidx.annotation.Nullable;

import com.mono.oregano.ui.auth.AuthFormState;

/**
 * Data validation state of the login form.
 */
class LoginFormState extends AuthFormState{

    LoginFormState(@Nullable Integer usernameError, @Nullable Integer passwordError) {
        super(usernameError,passwordError );
        this.isDataValid = false;
    }

    LoginFormState(boolean isDataValid) {
        super();
        this.emailError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }
}