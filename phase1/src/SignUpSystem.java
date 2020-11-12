import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class SignUpSystem {
    private UserManager userManager;

    public SignUpSystem(UserManager usermanager){
        this.userManager = usermanager;
    }

    public boolean signUp(String name, String email, String password, String typeOfUser) throws
            FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(new FileOutputStream("phase1/login.txt", true));
        Scanner scanner = new Scanner(new FileInputStream("phase1/login.txt"));
        String[] record;
        while (scanner.hasNextLine()) {
            record = scanner.nextLine().split(" ");
            if (record[1].equals(email)) {
                return false;
            }
        }
        printWriter.println(name + " " + email + " " + password + " " + typeOfUser);
        scanner.close();
        printWriter.close();
        return true;
    }
}
