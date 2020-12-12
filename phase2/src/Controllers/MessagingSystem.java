package Controllers;

import Entities.Event;
import Entities.Organizer;
import Entities.User;
import Gateway.EventSave;
import Gateway.UserSave;
import UseCase.EventManager;
import UseCase.UserManager;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Handles the messaging functionality of Users.
 * @author Tanya Thaker
 * @version 1.0
 */

public class MessagingSystem {

    private UserManager userManager;
    private EventManager eventManager;

    /**
     * Creates a MessagingSystem with a UserManager and EventManager.
     */
    public MessagingSystem() throws IOException {
        this.userManager = new UserSave().read();
        this.eventManager = new EventSave().read();
    }

    /**
     * Allows Users to message other Users, with restrictions based on the type of User that they are.
     * @param from the email representing the User who is sending the message
     * @param to a list containing all the Users that someone wants to message or Events, if the User is a Speaker
     * @param message the message that the User would like to send to someone
     * @return true or false, depending on whether sending the message was successful or not
     */
    public boolean sendMessage(String from, ArrayList<String> to, String message) throws IOException {
        if (!(userManager.checkUserExists(from))){
            return false;
        }
        if (userManager.findUser(from).userType() == 'A' || userManager.findUser(from).userType() == 'O' ||
                userManager.findUser(from).userType() == 'V' ){
            ArrayList<String> recipients = userManager.checkUsers(to); // returns array of existing users
            userManager.message(from, recipients, message);
            new UserSave().save(userManager);
            return true; }
        else if (userManager.findUser(from).userType() == 'S'){
            ArrayList<Event> events = new ArrayList<>();
            for (String s: to) {
                int eventID = Integer.parseInt(s);
                events.add(eventManager.findEvent(eventID));
            }
            userManager.message(from, eventManager.getAllEmails(events), message);
            new UserSave().save(userManager);
            return true;
        }
        return false;
    }

    public void updateNewOrganizer(User newOrg) throws IOException {
        if (userManager.getEmail() != null) {
            for (User emails : userManager.getUsers()) {
                if(!emails.getEmail().equals(newOrg.getEmail())) {
                    newOrg.addContact(emails.getEmail());
                    emails.addContact(newOrg.getEmail());
                }
            }
        }
        new UserSave().read();

    }

}

