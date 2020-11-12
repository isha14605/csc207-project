/* Login Controller */
public class LoginController {
    private UserManager userManager;
//    private User user;

    public LoginController(){
        userManager = new UserManager();

    }

//    public LoginController(UserManager userManager, User user) {
//        this.user = user;
//        this.userManager = userManager;
//    }

    public boolean checkLogIn(String email, String password) {
        if (userManager.verifyLogin(email, password))
            return true;
        return false;
    }

}
