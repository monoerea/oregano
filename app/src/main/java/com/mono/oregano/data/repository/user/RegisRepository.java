package com.mono.oregano.data.repository.user;

import com.mono.oregano.data.dataModel.users.LoggedInUser;
import com.mono.oregano.data.dataModel.users.baseUser;
import com.mono.oregano.data.network.FirebaseAuthInstance;
import com.mono.oregano.data.repository.Result;

public class RegisRepository {
    private static volatile RegisRepository instance;
    private FirebaseAuthInstance dataSource;

    private baseUser user = null;

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

    private void setRegisUser(baseUser user){
        this.user = user;
    }

    public Result<baseUser> register(String firstName, String midName, String lastName, String gender, String schoolNo, String email, String password){
        Result<baseUser> result= dataSource.register(firstName, midName, lastName, gender, schoolNo, email, password);
        if (result instanceof Result.Success) {
            setRegisUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }
}
