import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoginSystem {
    private UserManager userManager;

    public LoginSystem(UserManager usermanager){
        this.userManager = usermanager;
    }

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
