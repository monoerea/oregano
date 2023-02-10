package com.mono.oregano.ui.auth;

import androidx.annotation.Nullable;

public class AuthFormState {
    @Nullable
    protected Integer emailError;
    @Nullable
    protected Integer passwordError;

    protected Integer schoolNumError;
    protected boolean isDataValid;

    public AuthFormState(@Nullable Integer emailError, @Nullable Integer passwordError,  @Nullable Integer schoolNumError) {
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.schoolNumError = schoolNumError;
        this.isDataValid = false;
    }

    public AuthFormState(boolean isDataValid) {
        this.emailError = null;
        this.passwordError = null;
        this.schoolNumError = null;
        this.isDataValid = isDataValid;
    }

    public AuthFormState(@Nullable Integer emailError,@Nullable Integer passwordError) {
        this.emailError = emailError;
        this.passwordError = passwordError;
    }

    public AuthFormState() {

    }

    @Nullable
    public Integer getEmailError() {
        return emailError;
    }

    @Nullable
    public Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    public Integer getSchoolNumError() {
        return schoolNumError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }
}
