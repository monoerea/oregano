package com.mono.oregano.ui.auth;

public class AuthUserView {
    protected String fullName;
    //... other data fields that may be accessible to the UI

    protected AuthUserView(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}
