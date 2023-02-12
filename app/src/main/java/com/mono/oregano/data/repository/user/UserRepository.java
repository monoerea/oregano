package com.mono.oregano.data.repository.user;

import com.mono.oregano.data.dataModel.users.LoggedInUser;
import com.mono.oregano.data.dataModel.users.baseUser;
import com.mono.oregano.data.network.DataSources;
import com.mono.oregano.data.network.FirebaseAuthInstance;
import com.mono.oregano.data.network.FirebaseDBInstance;
import com.mono.oregano.data.repository.Result;
import com.mono.oregano.data.repository.baseRepository;

public class UserRepository extends baseRepository {
    static UserRepository instance;
    FirebaseDBInstance database;
    FirebaseAuthInstance auth;

    LoggedInUser logged;
    public static UserRepository getInstance(){
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }
    //private constructor class for creating instance
    UserRepository(){
        this.database = (FirebaseDBInstance) new FirebaseDBInstance().getInstance();
        this.auth = (FirebaseAuthInstance) new FirebaseAuthInstance().getInstance();
    }
    public Result getLoggedUser(){
        Result<LoggedInUser> loggedUser = auth.getLoggedUser();
        LoggedInUser logged;
        if (loggedUser instanceof Result.Error){
            return loggedUser;
        }
        logged = ((Result.Success<LoggedInUser>) loggedUser).getData();

        Result<baseUser> result= database.searchByID(logged, logged.getId());
        return result;
    }


    @Override
    public baseRepository getInstance(DataSources dataSources) {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }
}
