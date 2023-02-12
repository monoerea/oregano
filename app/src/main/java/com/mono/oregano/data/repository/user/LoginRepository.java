package com.mono.oregano.data.repository.user;

import android.app.Application;

import com.mono.oregano.data.dataModel.users.LoggedInUser;
import com.mono.oregano.data.network.FirebaseAuthInstance;
import com.mono.oregano.data.repository.Result;
import com.mono.oregano.data.dataModel.users.LoggedInUser;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
class LoginRepository {

    //TODO: Implement UserMutableLiveData
    private Application application;
    private static volatile LoginRepository instance;

    private FirebaseAuthInstance dataSource;


    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private LoginRepository(FirebaseAuthInstance dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(FirebaseAuthInstance dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }
    //method to check if there is a LoggedInUser instance
    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<LoggedInUser> login(String email, String password) {
        // handle login
        Result<LoggedInUser> result = dataSource.login(email, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }
}