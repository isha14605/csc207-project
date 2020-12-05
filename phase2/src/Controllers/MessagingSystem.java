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

    //Need to ask a question about the parameters here - Isha
    public boolean sendMessage(String from, ArrayList<String> to, String message){
        if (!(userManager.checkUserExists(from))){
            return false;
        }
        if (userManager.findUser(from).userType() == 'A' || userManager.findUser(from).userType() == 'O' ||
                userManager.findUser(from).userType() == 'V' ){
            ArrayList<String> recipients = userManager.checkUsers(to); // returns array of existing users
            userManager.message(from, recipients, message);
            return true;
        }
        return false;
    }

    public boolean speakerSendMessage(String from, ArrayList<Integer> to, String message) {
        ArrayList<Event> e = new ArrayList<>();
        for (Integer eventID : to) {
            e.add(eventManager.findEvent(eventID));
        }
        userManager.message(from, eventManager.getAllEmails(e), message);
        return true;
    }


}

