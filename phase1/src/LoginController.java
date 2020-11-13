import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoginController {
    private UserManager userManager;

    public LoginController(){
        this.userManager = new UserManager();
    }

    public boolean checkLogIn(String email, String password) {
        if (userManager.verifyLogin(email, password))
            return true;
        return false;
    }
}
