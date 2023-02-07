package com.mono.oregano.ui.auth;

import androidx.annotation.Nullable;

import com.mono.oregano.ui.auth.register.RegisteredUserView;

public class AuthResult {
    @Nullable
    private AuthUserView success;
    @Nullable
    private Integer error;

    public AuthResult(@Nullable Integer error) {
        this.error = error;
    }

    public AuthResult(@Nullable AuthUserView success) {
        this.success = success;
    }


    @Nullable
    public AuthUserView getSuccess() {
        return success;
    }

    @Nullable
    public Integer getError() {
        return error;
    }
}
