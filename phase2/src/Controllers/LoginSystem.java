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
     * Creates a LoginSystem with a UserManager.
     */
    public LoginSystem() throws IOException {
        this.userManager = new UserSave().read();
    }

    /**
     * Verifies the credentials entered by the User
     * @param email the email entered by the User when logging in
     * @param password the password entered by the User when logging in
     * @return true if login details are correct, false otherwise
     * @see UserManager#verifyLogin(String email, String password)
     */
    public boolean checkLogIn(String email, String password) {
        if(userManager.checkUserExists(email)){
            return userManager.verifyLogin(email, password);
        }
        return false;
    }

    /**
     * Returns true if account was created
     * @param email the email of the Entities.User
     * @param password the password of the Entities.User
     * @param name the name of the Entities.User
     * @param typeOfUser the type of User
     * @return boolean if an account was created.
     */

    public boolean createAccount(String email, String password, String name, String typeOfUser){
        if(userManager.checkUserExists(email)){
            return false; //user already exists
        }
        userManager.addUser(name, email, password, typeOfUser);
        return true;
    }
}
