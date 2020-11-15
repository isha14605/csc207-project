import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles signing up functionality for events.
 * @author Isha Sharma and Anushka Saini
 * @version 1.0
 */
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

    /**
     * Returns a list of events that an Attendee can attend, based on a specified date, start time, and end time. Checks
     * to see that there are events occurring on the specified date between the times provided.
     * @param date the date that the Attendee is interested in viewing events for
     * @param startTime the start time that the Attendee is interested in viewing events for
     * @param endTime the end time that the Attendee is interested in viewing events for
     * @return a list containing potential events that the Attendee is interested in attending for further perusal
     */
    // Allows the Attendee to browse the events and decide which ones they want to see based on date and time
    public ArrayList<Event> browseEvents(LocalDate date, LocalTime startTime, LocalTime endTime){
        ArrayList<Event> interestList = new ArrayList<Event>();
        ArrayList<Event> allEventsList = eM.getEvents();
        for (Event event: allEventsList) {
            if (event.getEventDate().equals(date) && event.getStartTime().equals(startTime)
                    && (event.getEndTime().isBefore(endTime) || event.getEndTime().equals(endTime))) {
                interestList.add(event);
            }
        }
        return interestList; // Return the list of events so the Attendee can browse
    }

    // Method to sign up an Attendee for an Event
    public void signUp(Attendee a, Event e){
        // Sign the Attendee up for the Event
        uM.signUp(a,e);
    }

}
