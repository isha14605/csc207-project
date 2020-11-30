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
    protected EventManager em = new EventManager();

    /**
     * UserManager Constructor
     */
    protected UserManager(){
//        this.addUser("s1", "s1", "s1", "speaker");
//        this.addUser("s2", "s2", "s2", "speaker");
//        this.addUser("s3", "s3", "s3", "speaker");
//        this.addUser("o1", "o1", "o1", "organizer");
//        this.addUser("o2", "o2", "o2", "organizer");
//        this.addUser("o3", "o3", "o3", "organizer");
//        this.addUser("a1", "a1", "a1", "attendee");
//        this.addUser("a2", "a2", "a2", "attendee");
//        this.addUser("a3", "a3", "a3", "attendee");
    }

    /** Allows a user to create a new account by checking if anyone with the same email id has already been registered.
     * @param name the name of the user.
     * @param password the password of this users account.
     * @param email the email of this users account.
     * @return true if the new user is created else false.
     */
    // DISCUSS WITH ISHA, CHECKING RELATED
    public boolean addUser(String name, String email, String password, String typeOfUser) {
        if (!UserManager.email.contains(email)) {
            UserManager.email.add(email);
            switch (typeOfUser) {
                case "organizer" -> {
                    users.add(new Organizer(name, password, email));
                    return true;
                }
                case "speaker" -> {
                    users.add(new Speaker(name, password, email));
                    return true;
                }
                case "attendee" -> {
                    users.add(new Attendee(name, password, email));
                    return true;
                }
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
     * @see Event#getAttendeeCapacity()
     * @see Event#getAttendeeEmails()
     * @see Event#getEventId()
     * @see Attendee#attendEvent(Integer)
     * @see Attendee#attendEvent(Integer)
     * @see Attendee#getEmail()
     */
    protected boolean signUp(Attendee attendee, Event event){
        if ((event.getAttendeeCapacity()> event.getAttendeeEmails().size()) &&
                !attendee.getEventsAttending().contains(event.getEventId())) {
            attendee.attendEvent(event.getEventId());
            event.addAttendee(attendee.getEmail());
            return true; //successfully signed up
        }
        return false; //cannot be signed up
    }

    /**
     * Cancels Attendee's registration for an Event. Checks to see that the specified Attendee is actually signed up
     * for the specified Event, before removing the Attendee from the Event.
     * @param attendee the Attendee who wants to cancel registration for an event
     * @param event the Event that the attendee would no longer like to attend
     * @see Attendee#getEventsAttending() 
     * @see Attendee#removeEvent(Integer)
     * @see Attendee#getEmail()
     * @see Event#removeAttendee(String)
     * @see EventManager#find_event(Integer)
     */
    protected boolean cancelRegistration(Attendee attendee, Integer event){
        if (attendee.getEventsAttending().contains(event)) {
            attendee.removeEvent(event);
            em.find_event(event).removeAttendee(attendee.getEmail());
            return true; //if event cancelled
        }
        return false; //if event not cancelled
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
        if (UserManager.email.contains(email)){
            for( User u: users){
                if (u.getEmail().equals(email) && u.getPassword().equals(password)){
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean inContact(String current, String email){
        ArrayList<String> u = findUser(current).getContacts();
        if (u.contains(email)){
            findUser(current).addContact(email);
            findUser(email).addContact(current);
            return false;
        }
        return true;
    }

    /**
     *
     * @param from, the user who wants to send the message.
     * @param to list of Users/ events that the sender wants to send the messages to
     * @param message the content of the message.
     */
    protected void message(User from, ArrayList<User> to, String message){
        //WHEN CALLING THIS NEED TO CHECK IF USERS EXISTS

        if (from.userType() == 'S'){
            speakerMessage(from, to, message);
        } else if (from.userType() == 'O'){
            organizerMessage(from, to, message);
        } else if (from.userType() == 'A') {
            attendeeMessage(from, to, message);
        }
    }

    // doesn't check anything at all! Still need to check if valid recipients
    private void organizerMessage(User from, ArrayList<User> to, String message){
        // to has to be attendees/ speakers only!
//        ArrayList<User> u = findUsers(to);

        for(User u1: to) {
            inContact(from.getEmail(), u1.getEmail());
            from.sendMessage(u1.getEmail(), message);
            u1.receiveMessage(from.getEmail(), message);
        }

    }


    // the only thing this method does is send messages, where to represents array of string of event id. It doesn't
    // check anything
    private void speakerMessage(User from, ArrayList<User> to, String message){
//        ArrayList<String> a = new ArrayList<String>();
//        for(String i : to){
//            if (em.find_event(Integer.parseInt(i)) != null){
//                a.addAll(em.find_event(Integer.parseInt(i)).getAttendeeEmails());
//            }
//        }
//        ArrayList<User> u = new ArrayList<User>();
//        u = findUsers(a);
        for(User i: to){
            inContact(from.getEmail(), i.getEmail());
            from.sendMessage(i.getEmail(), message);
            i.receiveMessage(from.getEmail(), message);
        }
    }


    /// the only thing this method does is send messages. It doesn't check anything
    private void attendeeMessage(User from, ArrayList<User> to, String message){
//        User from_user = findUser(from);
//        ArrayList<User> to_user = findUsers(to);
        for(User u : to){
            inContact(from.getEmail() ,u.getEmail());
            u.receiveMessage(from.getEmail(), message);
            from.sendMessage(u.getEmail(), message);
        }
    }


    protected ArrayList<User> findUsers(ArrayList<String> emails){
        ArrayList<User> user_obj = new ArrayList<User>();
        for(String i : emails){
            user_obj.add(this.findUser(i));
        }
        return user_obj;
    }

    /**
     * Identifies the User from the provided email address
     *
     * @return User associated with provided email address
     */
    protected User findUser(String email){
        int i;
        i = UserManager.email.indexOf(email);
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
