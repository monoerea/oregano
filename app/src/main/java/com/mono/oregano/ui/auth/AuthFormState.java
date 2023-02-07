package com.mono.oregano.ui.auth;

import androidx.annotation.Nullable;

public class AuthFormState {
    @Nullable
    private Integer emailError;
    @Nullable
    private Integer passwordError;

    private Integer schoolNumError;
    private boolean isDataValid;

    public AuthFormState(@Nullable Integer usernameError, @Nullable Integer passwordError,  @Nullable Integer schoolNumError) {
        this.emailError = usernameError;
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
