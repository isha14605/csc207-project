import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoginSystem {
    private UserManager userManager;

    public LoginSystem(UserManager usermanager){
        this.userManager = usermanager;
    }

    public boolean checkLogIn(String name, String email, String password, String typeOfUser) throws
            FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("login.txt"));
        String[] record;

        while (scanner.hasNextLine()) {
            record = scanner.nextLine().split(" ");
            if (record[0].equals(name) & record[1].equals(email) & record[2].equals(password) &
                    record[3].equals(typeOfUser)) {
                userManager.addUser(name, email, password, typeOfUser);
                return true;
            }
        }
        scanner.close();
        return false;
    }

}
