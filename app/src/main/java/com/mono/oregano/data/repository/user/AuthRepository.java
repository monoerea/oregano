package com.mono.oregano.data.repository.user;

import com.mono.oregano.data.dataModel.Model;
import com.mono.oregano.data.dataModel.users.LoggedInUser;
import com.mono.oregano.data.dataModel.users.User;
import com.mono.oregano.data.datasource.remote.DataSources;
import com.mono.oregano.data.datasource.remote.FirebaseAuthInstance;
import com.mono.oregano.data.datasource.remote.FirebaseDBInstance;
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
        Result<User> user =  regisInstance.register(firstName, midName, lastName, gender, schoolNo, email, password);

        if (user instanceof Result.Error){
            return user;
        }
            //TODO: create a new user document in collection
            //TODO: think about the system structure where to have multiple boilerplate code or not
            // via segmenting for each business object or pass a general model like rn
            //NEED TO TEST ASAP
        Result<Model> result = dataSource.insert(null,(Model) user);
        Result<Model> clients = dataSource.insert("Clients",(Model) user);

        if (clients instanceof Result.Error){
            return result;
        }
        if (result instanceof Result.Error){
            return result;
        }

        User toLogUser = (User) ((Result.Success<?>) result).getData();
        return logIn(toLogUser.getEmail(),toLogUser.getPassword());
    }
}
