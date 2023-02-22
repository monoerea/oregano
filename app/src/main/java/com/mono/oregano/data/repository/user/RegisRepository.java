package com.mono.oregano.data.repository.user;

import com.mono.oregano.data.dataModel.users.LoggedInUser;
import com.mono.oregano.data.dataModel.users.User;
import com.mono.oregano.data.datasource.remote.FirebaseAuthInstance;
import com.mono.oregano.data.repository.Result;

public class RegisRepository {
    private static volatile RegisRepository instance;
    private final FirebaseAuthInstance dataSource;

    private User user = null;

    private RegisRepository(FirebaseAuthInstance dataSource){this.dataSource = dataSource;}

    public static RegisRepository getInstance(FirebaseAuthInstance dataSource){
        if (instance == null) {
            instance = new RegisRepository(dataSource);
        }
        return instance;
    }

    public boolean isRegistered() {
        return user != null;
    }

    private void setRegisUser(User user){
        this.user = user;
    }

    public Result<User> register(String firstName, String midName, String lastName, String sex, String schoolNo, String college, String birthdate, String email, String password){
        Result<User> result= dataSource.register(firstName, midName, lastName, sex, schoolNo, college, birthdate, email, password);
        if (result instanceof Result.Success) {
            setRegisUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }
}
