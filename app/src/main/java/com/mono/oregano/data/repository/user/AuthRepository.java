package com.mono.oregano.data.repository.user;

import com.mono.oregano.data.model.Model;
import com.mono.oregano.data.model.users.LoggedInUser;
import com.mono.oregano.data.model.users.User;
import com.mono.oregano.data.model.users.baseUser;
import com.mono.oregano.data.remote.DataSources;
import com.mono.oregano.data.remote.FirebaseAuthInstance;
import com.mono.oregano.data.remote.FirebaseDBInstance;
import com.mono.oregano.data.repository.Result;
import com.mono.oregano.data.repository.baseRepository;
    //TODO: never pass in direct objects, just pass the id or object reference to follow the Single source of truth
public class AuthRepository extends baseRepository {
    private static AuthRepository instance;
    private FirebaseDBInstance dataSource;
    private FirebaseAuthInstance authInstance;
    private LoginRepository loginInstance;
    private RegisRepository regisInstance;

    private AuthRepository() {
        this.dataSource = (FirebaseDBInstance) new FirebaseDBInstance().getInstance();
        this.authInstance = (FirebaseAuthInstance) new FirebaseAuthInstance().getInstance();
        this.loginInstance = LoginRepository.getInstance(authInstance);
        this.regisInstance = RegisRepository.getInstance(authInstance);
    }

    public static AuthRepository getInstance(){
        if (instance == null) {
            instance = new AuthRepository();
        }
        return instance;
    }
    @Override
    public AuthRepository getInstance(DataSources dataSources) {
        if (instance == null) {
            instance = new AuthRepository();
        }
        return instance;
    }

    public void signOut(){
        loginInstance.logout();
    }
    public Result<LoggedInUser> logIn(String email, String password){
    Result<LoggedInUser> result = loginInstance.login(email, password);
    return result;
    }

    public Result<? extends Model> registerLogin(String firstName, String midName, String lastName, String gender, String schoolNo,String email, String password){
        Result<baseUser> regisResult =  regisInstance.register(firstName, midName, lastName, gender, schoolNo, email, password);

        if (regisResult instanceof Result.Error){
            return regisResult;
        }
            //TODO: create a new user document in collection
            //TODO: think about the system structure where to have multiple boilerplate code or not
            // via segmenting for each business object or pass a general model like rn
            //NEED TO TEST ASAP
        baseUser baseUser = ((Result.Success<baseUser>) regisResult).getData();
        User model = new User(baseUser.getId(),baseUser.getFirstName(),baseUser.getMidName(),baseUser.getLastName(),baseUser.getGender(),baseUser.getSchoolNo(),baseUser.getEmail(),baseUser.getPassword());
        Result<Model> result = dataSource.insert(null, model);
        if (result instanceof Result.Error){
            return result;
        }

        Result<Model> clients = dataSource.insert("Clients",model);
        return clients;
    }
}
