
/**
 * This class is the controller class responsible for the login function
 *
 * @author Hao Liu
 * @version 1.0
 */
public class LoginController {
    /**
     * Responsible for managing users
     */
    private UserManager userManager;

    /**
     * Constructor
     */
    public LoginController(){
        this.userManager = new UserManager();
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
        return userManager.verifyLogin(email, password);
    }
}
