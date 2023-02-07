package com.mono.oregano.ui.auth.register;

import com.mono.oregano.ui.auth.AuthUserView;

public class RegisteredUserView extends AuthUserView {

    //... other data fields that may be accessible to the UI

    RegisteredUserView(String displayName) {
        super(displayName);
    }

    public String getFullName(){
        return this.fullName;
    }
}
