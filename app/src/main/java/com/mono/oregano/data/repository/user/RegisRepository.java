package com.mono.oregano.data.repository.user;

import com.mono.oregano.data.model.UserModel;
import com.mono.oregano.data.model.users.LoggedInUser;
import com.mono.oregano.data.remote.FirebaseAuthInstance;
import com.mono.oregano.domain.util.Result;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisRepository {
    private static volatile RegisRepository instance;
    private FirebaseAuthInstance dataSource;

    private UserModel user = null;

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

    private void setRegisUser(UserModel user){
        this.user = user;
    }

    public Result<UserModel> register(String firstName, String midName, String lastName,
                                      String sex, String schoolNo,String college,
                                      String email, String password, String birthday){
        Result<UserModel> result = (Result<UserModel>) dataSource.register(firstName, midName, lastName, sex,
                    schoolNo, college, email, password, birthday);
        if (result instanceof Result.Success) {
            setRegisUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }

    /**
     * Parses string to Date
     * @param date
     * @return
     * @throws ParseException
     */
    private Date parseDate(String date) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date parsed = formatter.parse(date);
        return  parsed;
    }
}
