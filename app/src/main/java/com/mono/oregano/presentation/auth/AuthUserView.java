package com.mono.oregano.presentation.auth;

/**
 * Local class that stores the successful login or register data
 * For access of the local package.
 */
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
