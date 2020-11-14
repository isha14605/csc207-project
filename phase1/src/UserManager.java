import java.util.ArrayList;

/**
 * Manages all Users.
 *
 * @authors Isha Sharma and Tanya Thaker
 * @version 1.0
 */
public class UserManager{
    protected ArrayList<User> users = new ArrayList<User>();
    protected ArrayList<String> email = new ArrayList<String>();

    protected UserManager(){
        users.add(new User("LiuHao", "12345", "liuhao@gmail.com"));
        users.add(new User("Test1", "12345", "Test1@gmail.com"));
        users.add(new User("Test2", "12345", "Test2@gmail.com"));
        email.add("liuhao@gmail.com");
        email.add("Test1@gmail.com");
        email.add("Test2@gmail.com");
    }

    protected boolean createUser(String name, String password, String email){
        if (!this.email.contains(email)){
            User u1 = new User(name, password, email);
            users.add(u1);
            return true;
        }
        return false;
    }

    public void addUser(String name, String email, String password, String typeOfUser) {
        if (typeOfUser.equals("organizer")) {
            users.add(new Organizer(name, email, password));
        }
        else if (typeOfUser.equals("speaker")) {
            users.add(new Speaker(name, email, password));
        }
        else if (typeOfUser.equals("attendee")) {
            users.add(new Attendee(name, email, password));
        }
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
    protected void signUp(Attendee attendee, Event event){
        if ((event.getEventRoom().getRoomCapacity() < event.getAttendees().size()) &&
                !attendee.getEventsAttending().contains(event)) {
            attendee.attendEvent(event);
            event.addAttendee(attendee);
        }
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

    protected boolean message(Messageable from, Messageable to, String message){
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

    private boolean organizerMessage(Organizer from, Messageable to, String message){
        if (users.contains(to)){
            from.sendMessage(from, message);
            to.receiveMessage((User) to, message);
            return true;
        }
        return false;
    }


    private boolean speakerMessage(Speaker from, Messageable to, String message){
        for (Talk t: from.getTalksSpeaking()){
            Event e = t.getEvent();
            if (e.getAttendees().contains(to)){
                to.receiveMessage(from, message);
                from.sendMessage((User) to, message);
                return true;

            }
        }
        return false;
    }

    private boolean attendeeMessage(Attendee from, Messageable to, String message){
        if (to.userType() == 'A' |  to.userType()=='S'){
            to.receiveMessage(from, message);
            from.sendMessage((User) to, message);
            return true;
        }
        return false;
    }



}
