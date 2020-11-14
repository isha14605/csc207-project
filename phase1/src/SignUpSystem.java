import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class SignUpSystem {
    private UserManager userManager;

    public SignUpSystem(UserManager usermanager){
        this.userManager = usermanager;
    }

    public boolean signUpCheck(String name, String email, String password, String typeOfUser) throws
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

    UserManager uM = new UserManager(); // New instance of UserManager
    EventManager eM = new EventManager(); // New instance of EventManager

    // Method to allow the Attendee to browse the events and decide which ones they want to see.
    public ArrayList browseTalks(LocalDateTime ldt){
        ArrayList<Talk> allTalksList = new ArrayList<Talk>();
        /* Need to get a list of all the events from the event manager (method is not there) and
        then keep adding each of those events to the list in this method
         */
        return allTalksList; // Return the list of events so the Attendee can browse
    }

    // Method to sign up an Attendee for an Event.
    public void signUp(Attendee a, Event e){
        // Sign the Attendee up for the Event
        uM.signUp(a,e);
    }

}
