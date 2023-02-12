package com.mono.oregano.ui.auth;

import androidx.annotation.Nullable;

public class AuthFormState {
    @Nullable
    protected Integer emailError;
    @Nullable
    protected Integer passwordError;
    @Nullable
    protected Integer schoolNumError;
    @Nullable
    protected Integer firstNameError;
    @Nullable
    protected Integer midNameError;
    @Nullable
    protected Integer lastNameError;
    protected boolean isDataValid;

    public AuthFormState(@Nullable Integer firstNameError,
                         @Nullable Integer midNameError,
                         @Nullable Integer lastNameError,
                         @Nullable Integer emailError,
                         @Nullable Integer passwordError,
                         @Nullable Integer schoolNumError) {
        this.firstNameError =firstNameError;
        this.midNameError = midNameError;
        this.lastNameError = lastNameError;
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.schoolNumError = schoolNumError;
        this.isDataValid = false;
    }

    public AuthFormState(boolean isDataValid) {
        this.firstNameError =null;
        this.midNameError = null;
        this.lastNameError = null;
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
    public Integer getFirstNameError() {
        return firstNameError;
    }

    @Nullable
    public Integer getMidNameError() {return midNameError;}

    @Nullable
    public Integer getLastNameError() {return lastNameError;}
    @Nullable
    public Integer getSchoolNumError() {
        return schoolNumError;
    }

    @Nullable
    public Integer getEmailError() {
        return emailError;
    }

    @Nullable
    public Integer getPasswordError() {return passwordError;}
    public boolean isDataValid() {
        return isDataValid;
    }
}
