package Controllers;

import Entities.Event;
import Gateway.EventSave;
import Gateway.UserSave;
import UseCase.EventManager;
import UseCase.UserManager;

import java.util.ArrayList;

/**
 * Handles the messaging functionality of Users.
 * @author Tanya Thaker
 * @version 1.0
 */

public class MessagingSystem {

    private UserManager userManager;
    private EventManager eventManager;


    public MessagingSystem() {
        this.userManager = new UserSave().read();
        this.eventManager = new EventSave().read();
    }

    // Javadoc needs to be done - Isha
    public boolean sendMessage(String from, ArrayList<String> to, String message){
        if (!(userManager.checkUserExists(from))){
            return false;
        }
        if (userManager.findUser(from).userType() == 'A' || userManager.findUser(from).userType() == 'O' ||
                userManager.findUser(from).userType() == 'V' ){
            ArrayList<String> recipients = userManager.checkUsers(to); // returns array of existing users
            userManager.message(from, recipients, message);
            return true;
        } else if (userManager.findUser(from).userType() == 'S'){
            ArrayList<Event> events = new ArrayList<>();
            for (String s: to) {
                int eventID = Integer.parseInt(s);
                events.add(eventManager.findEvent(eventID));
            }
            userManager.message(from, eventManager.getAllEmails(events), message);
            return true;
        }
        return false;
    }

}

