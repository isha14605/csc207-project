package Controllers;

import UseCase.EventManager;
import UseCase.UserManager;
import Entities.Event;


import java.util.ArrayList;

/**
 * Handles the messaging functionality of Users.
 * @author Tanya Thaker
 * @version 1.0
 */

public class MessagingSystem {

    UserManager um = new UserManager(); // NOT HERE
    EventManager em = new EventManager(); // NOT HERE


    public MessagingSystem() {}

//    //Need to fix this method - Isha
//    public boolean sendMessage(String from, ArrayList<String> to, String message){
//        if (!(um.checkUserExists(from))){
//            return false;
//        }
//        if (um.findUser(from).userType() == 'A' || um.findUser(from).userType() == 'O' ||
//                um.findUser(from).userType() == 'V' ){
//            ArrayList<String> recipients = um.checkUsers(to); // returns array of existing users //WORK ON THIS!
//            um.message(from, recipients, message);
//            return true;
//        } else if (um.findUser(from).userType() == 'S'){
//            ArrayList<Event> e = em.findEvent(to); // returns the events they are part of based on input
//            ArrayList<String> attendees = new ArrayList<String>();
//            for(Event i: e){
//                attendees.addAll(i.getAttendeeEmails());
//            }
//            um.message(from, attendees, message);
//            return true;
//        }
//        return false;
//    }


}

