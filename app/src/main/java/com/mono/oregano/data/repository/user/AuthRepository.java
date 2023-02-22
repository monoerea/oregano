package com.mono.oregano.data.repository.user;

import com.mono.oregano.data.model.Model;
import com.mono.oregano.data.model.UserModel;
import com.mono.oregano.data.model.users.LoggedInUser;
import com.mono.oregano.data.remote.DataSources;
import com.mono.oregano.data.remote.FirebaseAuthInstance;
import com.mono.oregano.data.remote.FirebaseDBInstance;
import com.mono.oregano.data.repository.baseRepository;
import com.mono.oregano.domain.util.Result;

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

    public Result<? extends Model> registerLogin(String firstName, String midName, String lastName,
                                                 String sex, String schoolNo,String college,
                                                 String email, String password, String birthday){
        Result<UserModel> regisResult =  regisInstance.register(firstName, midName, lastName,
                sex, schoolNo,college, email, password,birthday);

        if (regisResult instanceof Result.Error){
            return regisResult;
        }
            //TODO: create a new user document in collection
            //TODO: think about the system structure where to have multiple boilerplate code or not
            // via segmenting for each business object or pass a general model like rn
            //NEED TO TEST ASAP
        UserModel user = ((Result.Success<UserModel>) regisResult).getData();
        UserModel model = new UserModel(user.getId(),user.getFirstName(),user.getMidName(),user.getLastName(),user.getSex(),user.getSchoolNo(),user.getCollege(),user.getEmail(),user.getPassword(), user.getBirthday(), user.getCreatedAt());
        Result<Model> result = dataSource.insert(null, model);
        if (result instanceof Result.Error){
            return result;
        }

        Result<Model> clients = dataSource.insert("Clients",model);
        return clients;
    }
}
