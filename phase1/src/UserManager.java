import java.util.ArrayList;

/**
 * Manages all Users.
 *
 * @author Isha Sharma and Tanya Thaker
 * @version 1.0
 */
public class UserManager{
    protected static ArrayList<User> users = new ArrayList<User>();
    protected static ArrayList<String> email = new ArrayList<String>();

    /**
     * UserManager Constructor
     */
    protected UserManager(){
        this.addUser("s1", "s1", "s1", "speaker");
        this.addUser("s2", "s2", "s2", "speaker");
        this.addUser("s3", "s3", "s3", "speaker");
        this.addUser("o1", "o1", "o1", "organizer");
        this.addUser("o2", "o2", "o2", "organizer");
        this.addUser("o3", "o3", "o3", "organizer");
        this.addUser("a1", "a1", "a1", "attendee");
        this.addUser("a2", "a2", "a2", "attendee");
        this.addUser("a3", "a3", "a3", "attendee");
    }

    /** Allows a user to create a new account by checking if anyone with the same email id has already been registered.
     * @param name the name of the user.
     * @param password the password of this users account.
     * @param email the email of this users account.
     * @return true if the new user is created else false.
     */
    public boolean addUser(String name, String email, String password, String typeOfUser) {
        if (!UserManager.email.contains(email)) {
            UserManager.email.add(email);
            if (typeOfUser.equals("organizer")) {
                users.add(new Organizer(name, password, email));
                return true;
            } else if (typeOfUser.equals("speaker")) {
                users.add(new Speaker(name, password, email));
                return true;
            } else if (typeOfUser.equals("attendee")) {
                users.add(new Attendee(name, password, email));
                return true;
            }
        }
        return false;
    }

    /**
     * Allows an Attendee to sign up for an Event. Checks to see if the Room where the Event is held is not at full
     * capacity and that the Attendee has not already signed up for the Event, before signing up the specified Attendee
     * for the specified Event.
     * @param attendee the Attendee who wants to sign up for an event
     * @param event the Event that the attendee would like to attend
     * @see Attendee#getEventsAttending() 
     * @see Attendee#attendEvent(Event) 
     * @see Event#getEventRoom() 
     * @see Event#getAttendees()
     * @see Event#addAttendee(Attendee) 
     * @see Room#getRoomCapacity() 
     */
    protected boolean signUp(Attendee attendee, Event event){
        if ((event.getEventRoom().getRoomCapacity() > event.getAttendees().size()) &&
                !attendee.getEventsAttending().contains(event)) {
            attendee.attendEvent(event);
            event.addAttendee(attendee);
            return true;
        }
        return false;
    }

    /**
     * Cancels Attendee's registration for an Event. Checks to see that the specified Attendee is actually signed up
     * for the specified Event, before removing the Attendee from the Event.
     * @param attendee the Attendee who wants to cancel registration for an event
     * @param event the Event that the attendee would no longer like to attend
     * @see Attendee#getEventsAttending() 
     * @see Attendee#removeEvent(Event)
     * @see Event#removeAttendee(Attendee) 
     */
    protected void cancelRegistration(Attendee attendee, Event event){
        if (attendee.getEventsAttending().contains(event)) {
            attendee.removeEvent(event);
            event.removeAttendee(attendee);
        }
    }

    /**
     * Verifies the login details of the user logging in.
     * @param email the email entered by the user
     * @param password the password entered by the user
     * @return boolean true if login details are correct.
     * @see User#getEmail()
     * @see User#getPassword()
    */
    protected boolean verifyLogin(String email, String password){
        if (this.email.contains(email)){
            for(User u: users){
                if (u.getEmail().equals(email) && u.getPassword().equals(password)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param from, the user who wants to send the message.
     * @param to the user who is the recipient of this message.
     * @param message the content of the message.
     * @return true if the message was successfully sent.
     */
    protected boolean message(User from, User to, String message){
        boolean t = false;

        if (from.userType() == 'S'){
            t = speakerMessage((Speaker) from, to, message);
        } else if (from.userType() == 'O'){
            t = organizerMessage((Organizer) from, to, message);
        } else if (from.userType() == 'A') {
            t = attendeeMessage((Attendee) from, to, message);
        }
        return t;
    }

    /**
     * Sends an Organizer message
     * @param from the Organizer sending the message
     * @param to the User receiving the message
     * @param message content of message to be sent
     * @return true if the message was sent successfully
     */
    private boolean organizerMessage(Organizer from, User to, String message){
        if (users.contains(to)){
            from.sendMessage(from, message);
            to.receiveMessage(to, message);
            return true;
        }
        return false;
    }


    /**
     * Sends a Speaker message
     *
     * @return true if the message was sent successfully
     */
    private boolean speakerMessage(Speaker from, User to, String message){
        from.addContact(to);
        for (Talk t: from.getTalksSpeaking()){
            Event e = t.getEvent();
            if (e.getAttendees().contains(to)){
                to.receiveMessage(from, message);
                from.sendMessage(to, message);
                return true;

            }
        }
        return false;
    }

    /**
     * Sends an Attendee message
     *
     * @return true if the message was sent successfully
     */
    private boolean attendeeMessage(Attendee from, User to, String message){
        if (to.userType() == 'A' |  to.userType()=='S'){
            if (!from.getContacts().contains(to)){
                from.addContact(to);
            }
            to.receiveMessage(from, message);
            from.sendMessage(to, message);
            return true;
        }
        return false;
    }

    /**
     * Identifies the User from the provided email address
     *
     * @return User associated with provided email address
     */
    protected User findUser(String email){
        int i;
        i = UserManager.email.indexOf(email);
        //System.out.println(i);
        return UserManager.users.get(i);
    }

    /**
     * Verifies if there is a User stored who is associated with the provided email address
     *
     * @return true if the User exists
     */
    protected boolean checkUserExists(String email) {
        return UserManager.email.contains(email);
    }
}
