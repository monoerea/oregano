package com.mono.oregano.ui.auth.login;

import com.mono.oregano.ui.auth.AuthUserView;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView extends AuthUserView {

    //... other data fields that may be accessible to the UI

    LoggedInUserView(String fullName) {
        super(fullName);
    }
    public String getFullName(){
        return this.fullName;
    }
}