import java.util.ArrayList;

public class User_Manager {
    protected ArrayList<User> users = new ArrayList<User>();
    protected ArrayList<String> email = new ArrayList<String>();

    protected User_Manager(){}

    protected boolean add_user(String name, String password, String email){
        if (!this.email.contains(email)){
            User u1 = new User(name, password, email);
            users.add(u1);
            return true;
        }
        return false;
    }

    protected boolean sign_up_event(Attendee person, Event event){
        if (!this.users.contains(event)) {
            return true;
        }
        return false;
    }
    
    protected boolean verify_login(String email, String password){
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
            case "Speaker" -> speaker_message((Speaker) from, to, message);
            case "Organizer" -> false;
            default -> true;
        };
        return t;
    }

    private boolean attendee_message(String type, User to, User from, String message){return true;}


    private boolean speaker_message(Speaker from, User to, String message){
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
