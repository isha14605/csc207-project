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

    protected boolean message(String type, User from, User to, String message){
        boolean t = false;

        if (type.equals("Speaker")){
            t = speakerMessage((Speaker) from, to, message);
        } else if (type.equals("Organizer")){
            t = organizerMessage((Organizer) from, to, message);
        } else {
            t = true;
        }
        return t;
    }

    private boolean organizerMessage(Organizer from, User to, String message){
        if (users.contains(to)){
            from.send_message(from, message);
            to.receive_message(to, message);
            return true;
        }
        return false;
    }


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
