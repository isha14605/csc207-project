package Controllers;

import Gateway.UserSave;
import UseCase.UserManager;

import java.io.IOException;

/**
 * This class is the controller class responsible for the login function
 *
 * @author Hao Liu
 * @version 1.0
 */
public class LoginSystem {
    /**
     * Responsible for managing users
     */
    private UserManager userManager;

    /**
     * Constructor
     */
    public LoginSystem() throws IOException {
        this.userManager = new UserSave().read();
    }

    /**
     * Verify the credentials entered by the user
     *
     * @param email The email name entered by the user when logging in
     * @param password Password entered by the user when logging in
     * @return boolean true if login details are correct.
     * @see UserManager#verifyLogin(String email, String password)
     */

    public boolean checkLogIn(String email, String password) {
        if(userManager.checkUserExists(email)){
            return userManager.verifyLogin(email, password);
        }
        return false;
    }

    public boolean createAccount(String email, String password, String name, String typeOfUser){
        if(userManager.checkUserExists(email)){
            return false; //user already exists
        }
        userManager.addUser(name, email, password, typeOfUser);
        return true;
    }
}
