import java.util.ArrayList;

public class UserManager {
    protected ArrayList<User> users = new ArrayList<User>();
    protected ArrayList<String> email = new ArrayList<String>();

    protected UserManager(){}

    protected boolean createUser(String name, String password, String email){
        if (!this.email.contains(email)){
            User u1 = new User(name, password, email);
            users.add(u1);
            return true;
        }
        return false;
    }

    // Allows an Attendee to sign up for an event
    protected boolean signUp(Attendee attendee, Event event){
        if ((event.getEvent_room().getRoom_capacity() < event.getAttendees().size()) &&
                !attendee.getEventsAttending().contains(event)) {
            attendee.attendEvent(event);
            event.add_attendee(attendee);
            return true;
        }
        return false;
    }

    // Allows an Attendee to cancel their registration for an event
    protected boolean cancelRegistration(Attendee attendee, Event event){
        if (attendee.getEventsAttending().contains(event)) {
            attendee.removeEvent(event);
            event.remove_attendee(attendee);
            return true;
        }
        return false;
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

    protected boolean message(String type, User from, User to, String message, User u){
        Boolean t = switch (type) {
            case "Speaker" -> speakerMessage((Speaker) from, to, message);
            case "Organizer" -> false;
            default -> true;
        };
        return t;
    }

    private boolean attendeeMessage(String type, User to, User from, String message){return true;}


    private boolean speakerMessage(Speaker from, User to, String message){
        for (Talk t: from.getTalks_speaking()){
            Event e = t.getEvent();
            if (e.getAttendees().contains(to)){
                to.receive_message(from, message);
                from.send_message(to, message);
                return true;

            }
        }
        return false;
    }



}