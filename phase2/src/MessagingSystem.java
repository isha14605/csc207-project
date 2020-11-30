import java.util.ArrayList;

/**
 * Handles the messaging functionality of Users.
 * @author Tanya Thaker
 * @version 1.0
 */

public class MessagingSystem {

    UserManager um = new UserManager();
    EventManager em = new EventManager();


    public MessagingSystem() {
    }

    public boolean sendMessage(String from, ArrayList<String> to, String message){
        if (!(um.checkUserExists(from))){
            return false;
        }
        if (um.findUser(from).userType() == 'A' || um.findUser(from).userType() == 'O'){
            ArrayList<User> recipeints = um.findUsers(checkUsers(to)); // returns array of existing users
            um.message(um.findUser(from), recipeints, message);
            return true;
        } else if (um.findUser(from).userType() == 'S'){
            ArrayList<Event> e = em.findEvents(to);
            ArrayList<User> attendees = new ArrayList<User>();
            for(Event i: e){
                attendees.addAll(um.findUsers(i.getAttendeeEmails()));
            }
            um.message(um.findUser(from), attendees, message);
            return true;
        }
        return false;

    }

    private ArrayList<String> checkUsers(ArrayList<String> from){
        ArrayList<String> u = new ArrayList<String>();
        for(String i: from){
            if (um.checkUserExists(i)){
                u.add(i);
            }
        }
        return u;
    }
}

