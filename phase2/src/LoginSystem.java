import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
// delete this file

public class LoginSystem {
    private UserManager userManager;

    /**
     * LoginSystem Constructor
     */
    public LoginSystem(UserManager usermanager){
        this.userManager = usermanager;
    }

    /**
     * Verifies User login information
     * @param email email of the User
     * @param password password of the User
     * @return true if login successful
     */
    public boolean checkLogIn(String email, String password) throws
            FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("phase1/login.txt"));
        String[] record;

        while (scanner.hasNextLine()) {
            record = scanner.nextLine().split(" ");
            if (record[1].equals(email) & record[2].equals(password)) {
                userManager.addUser(record[0], email, password, record[3]);
                return true;
            }
        }
        scanner.close();
        return false;
    }
}
