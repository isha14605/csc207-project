package UseCase;

import Entities.*;
import Gateway.ConferenceSave;
import Gateway.EventSave;
import Gateway.UserSave;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Manages all Users.
 *
 * @author Isha Sharma and Tanya Thaker
 * @version 1.0
 */
public class UserManager implements Serializable {
    private final ArrayList<User> users;
    private final ArrayList<String> email;
    private final ArrayList<String> speaker;
    private final ArrayList<Speaker> speakerAccounts;
    private final ArrayList<String> OrganizerAccounts;
    public EventManager em; // can we do this?


    /**
     * UseCase.UserManager Constructor
     */
    public UserManager() throws IOException {
        users = new ArrayList<>();
        email = new ArrayList<>();
        speaker = new ArrayList<>();
        speakerAccounts = new ArrayList<>();
        OrganizerAccounts = new ArrayList<>();
        addUser("Admin","Admin","Admin","Organizer");

    }

    public ArrayList<User> getUsers() {
        return users;
    }


    /**
     * Allows a user to create a new account by checking if anyone with the same email id has already been registered.
     *
     * @param name     the name of the user.
     * @param password the password of this users account.
     * @param email    the email of this users account.
     */
    public void addUser(String name, String email, String password, String typeOfUser) {
        if (typeOfUser.equals("Organizer")) {
            Organizer o = new Organizer(name, password, email);
            users.add(o);
            OrganizerAccounts.add(o.getName());
            addListContacts(o, this.email);
            updateAdmin(users.get(users.size()-1).getEmail());
            this.email.add(email);

        }

        if (typeOfUser.equals("Speaker")) {
            Speaker s = new Speaker(name, password, email);
            users.add(s);
            speakerAccounts.add(s);
            speaker.add(email);
            updateAdmin(users.get(users.size()-1).getEmail());
            this.email.add(email);
        }

        if (typeOfUser.equals("Attendee")) {
            Attendee a = new Attendee(name, password, email);
            users.add(a);
            this.email.add(email);
            addListContacts(a, OrganizerAccounts);
            updateAdmin(users.get(users.size()-1).getEmail());

        }

        if (typeOfUser.equals("VIP")) {
            users.add(new VIP(name, password, email));
            this.email.add(email);
            updateAdmin(users.get(users.size()-1).getEmail());
        }
    }

    public ArrayList<String> getSpeaker() {
        return speaker;
    }

    public Speaker findSpeaker(String email){
        for(Speaker s: speakerAccounts){
            if(s.getEmail().equals(email)){
                return s;
            }
        }
        return null;
    }

    public ArrayList<String> getSpeakerAccountsEmails() {
        ArrayList<String> name = new ArrayList<>();
        for(Speaker speaker: speakerAccounts){
            if((speaker.userType() == 'O')){
                name.add(speaker.getEmail());
            }
        }
        return name;
    }

    public void updateAdmin(String email){
        if(!email.equals("Admin")) {
            findUser("Admin").addContact(email);
            findUser(email).addContact("Admin");
        }
    }


    /**
     * Verifies the login details of the user logging in.
     * @param email the email entered by the user
     * @param password the password entered by the user
     * @return boolean true if login details are correct.
     * @see User#getEmail()
     * @see User#getPassword()
     */
    public boolean verifyLogin(String email, String password){
        if (this.email.contains(email)){
            for( User u: users){
                if (u.getEmail().equals(email) && u.getPassword().equals(password)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Allows an Entities.Attendee to sign up for an Entities.Event.
     * @param attendee the Entities.Attendee who wants to sign up for an event
     * @param event the Entities.Event that the attendee would like to attend
     * @see Event#getAttendeeCapacity()
     * @see Event#getAttendeeEmails()
     * @see Event#getEventId()
     * @see Attendee#attendEvent(Integer)
     * @see Attendee#getEventsAttending()
     * @see Attendee#attendEvent(Integer)
     * @see Attendee#getEmail()
     * @see Attendee#attendConference(String)
     */
    public boolean signUpEvent(Attendee attendee, Event event, Conference c){
        if(event.getAttendeeEmails().contains(attendee.getEmail())){
            return false;
        }
        if(c== null && !(attendee.getEventsAttending().contains(event.getEventId()))
                && event.getAttendeeCapacity() > event.getAttendeeEmails().size()){
            attendee.attendEvent(event.getEventId());
            event.addAttendee(attendee.getEmail());

            addListContacts(attendee, event.getAttendeeEmails());

            return true;
        } else if ( c!= null && !(attendee.getEventsAttending().contains(event.getEventId()))
                && event.getAttendeeCapacity() > event.getAttendeeEmails().size()){
            attendee.attendEvent(event.getEventId());
            event.addAttendee(attendee.getEmail());
            attendee.addContact(c.getName());

            addListContacts(attendee, event.getAttendeeEmails());

            return true;
        }
        return false;
    }

    /**
     * Cancels Entities.Attendee's registration for an Entities.Event. Checks to see that the specified
     * Entities.Attendee is actually signed up
     * for the specified Entities.Event, before removing the Entities.Attendee from the Entities.Event.
     * @param attendee the Entities.Attendee who wants to cancel registration for an event
     * @param event the Entities.Event that the attendee would no longer like to attend
     * @param c the Entities.Conference that the event is part of
     * @see Attendee#getEventsAttending()
     * @see Attendee#removeEvent(Integer)
     * @see Attendee#getEmail()
     * @see Attendee#removeConference(String)
     * @see Event#removeAttendee(String)
     * @see Event#getEventId()
     * @see Event#getAttendeeEmails()
     * @see Event#getAttendeeCapacity()
     * @see Conference#getEventIds()
     */
    public boolean cancelRegistrationEvent(Attendee attendee, Event event, Conference c) throws IOException {
        int ctr = 0;
        if (!attendee.getEventsAttending().contains(event.getEventId())){
            return false;
        }
        if(!(c== null)){
            for(int e: c.getEventIds()){
                em = new EventSave().read();
                if (em.findEvent(e).getAttendeeEmails().contains(attendee.getEmail())){ctr = ctr +1;}
                // counts if attendee is part of more than 1
            }
            if (ctr == 1){
                attendee.removeConference(c.getName());
            }
        }
        event.removeAttendee(attendee.getEmail());
        attendee.removeEvent(event.getEventId());
        removeListContacts(attendee, event.getAttendeeEmails());
        if(event.eventType().equals("Panel")){
            Entities.Panel p = (Panel) event;
            for(String u: p.getSpeakerEmails()){
                removeSpeakerContacts(attendee, findUser(u));
            }
        } else if(event.eventType().equals("Talk")){
            Entities.Talk t = (Talk) event;
            if(!(t.getSpeakerEmail() ==null)) {
                removeSpeakerContacts(attendee, findUser(t.getSpeakerEmail()));
            }
        }
        return true;
    }

    /**
     * Cancels Entities.Organizer's registration for an Entities.Event.
     * @param organizer Entities.Organizer to be removed from Entities.Event.
     * @param event Entities.Event Entities.Organizer is to be removed from.
     */
    public void cancelRegistrationEventOrganizer (Organizer organizer, Event event) {
        event.removeOrganizer(organizer.getEmail());
        organizer.removeEvent(event.getEventId());
    }

    /**
     * Cancels Entities.Speaker's registration for an Entities.Event.
     * @param speaker Entities.Speaker to be removed from Entities.Event.
     * @param event Entities.Event Entities.Speaker is to be removed from.
     */
    public void cancelRegistrationEventSpeaker (Speaker speaker, Event event) {
        if (event instanceof Talk) {
            ((Talk) event).removeSpeaker();
        } else if (event instanceof Panel) {
            ((Panel) event).removeSpeakers();
        }
        if(!(speaker ==null)) {
            speaker.removeEvent(event.getEventId());
        }
    }


    /**
     * Signs a vip-only event and updates the points associated with a VIP attending an event
     * @param v the Entities.VIP that is being signed up for an event
     * @param event the event that the vip is being added to
     * @param c the conference to which the event belongs
     */
    public void signUpVip(VIP v, Event event, Conference c){
        boolean flag = signUpEvent(v, event, c);
        if (flag){
            if(event.isVipOnly()){
                v.attendVipEvent(event.getEventId());
                v.addPoints(50);
                updateMemberStatus(v);
            } else if(!event.isVipOnly()){
                v.addPoints(10);
                updateMemberStatus(v);
            }
        }
    }

    /**
     * Cancels vip from attending a vip-only event and updates the points associated with cancellation
     * @param v the Entities.VIP that is being removed from an event
     * @param event the event that the vip is being removed from
     * @param c the conference to which the event belongs
     */
    public void cancelVip(VIP v, Event event, Conference c) throws IOException {
        boolean flag = cancelRegistrationEvent(v, event, c);
        if (flag){
            if(event.isVipOnly()){
                v.removeVipEvent(event.getEventId());
                v.removePoints(50);
                updateMemberStatus(v);
            } else if(!event.isVipOnly()){
                v.removePoints(10);
                updateMemberStatus(v);
            }
        }
    }

    /**
     * Signs up Entities.Attendee for the conference
     * @param attendee the attendee who wishes to sign up for a conference
     * @param event the events that are part of the conference
     * @param c the conference the attendee wishes to attend
     * @see Attendee#attendConference(String)
     * @see Event#getAttendeeEmails()
     * @see Conference#getName()
     */
    public void attendConference(Attendee attendee, ArrayList<Event> event, Conference c){
        attendee.attendConference(c.getName());
        for(Event e: event){
            addListContacts(attendee, e.getAttendeeEmails());
        }
    }

    /**
     * Cancels registration of Entities.Attendee for the conference
     * @param attendee the attendee who wishes to cancel registration  for a conference
     * @param event the events that are part of the conference
     * @param c the conference the attendee wishes to cancel registration for
     * @see  Attendee#removeConference(String)
     * @see  Conference#getName()
     * @see Event#getAttendeeEmails()
     */
    public void cancelRegistrationConference(Attendee attendee, ArrayList<Event> event, Conference c){
        attendee.removeConference(c.getName());
        for(Event e: event){
            addListContacts(attendee, e.getAttendeeEmails());
        }
    }

    /**
     * @param from, the user who wants to send the message.
     * @param to list of Users/ events that the sender wants to send the messages to
     * @param message the content of the message.
     */
    public void message(String from, ArrayList<String> to, String message){
        //WHEN CALLING THIS NEED TO CHECK IF USERS EXISTS

        if (findUser(from).userType() == 'S'){
            speakerMessage(from, to, message);
        } else if (findUser(from).userType()  == 'O'){
            organizerMessage(from, to, message);
        } else if (findUser(from).userType() == 'A') {
            attendeeMessage(from, to, message);
        }  else if(findUser(from).userType()  == 'V'){
            attendeeMessage(from, to, message);
        }
    }

    // doesn't check anything at all! Still need to check if valid recipients
    private void organizerMessage(String from, ArrayList<String> to, String message){
        // to has to be attendees/ speakers only!
        ArrayList<User> u = findUsers(to);
        User from_user = findUser(from);

        for(User u1: u) {
//            inContact(from, u1.getEmail());
            from_user.sendMessage(u1.getEmail(), message);
            u1.receiveMessage(from, message);
        }

    }


    // the only thing this method does is send messages, where to represents array of string of event id. It doesn't
    // check anything
    private void speakerMessage(String from, ArrayList<String> to, String message){
        ArrayList<String> a = new ArrayList<String>();
        for(String i : to){
            if (em.findEvent(Integer.parseInt(i)) != null){
                a.addAll(em.findEvent(Integer.parseInt(i)).getAttendeeEmails());
            }
        }
        ArrayList<User> u = findUsers(a);
//        u = findUsers(a);
        for(User i: u){
//            inContact(from, i.getEmail());
            findUser(from).sendMessage(i.getEmail(), message);
            i.receiveMessage(from, message);
        }
    }


    /// the only thing this method does is send messages. It doesn't check anything
    private void attendeeMessage(String from, ArrayList<String> to, String message){
        User from_user = findUser(from);
        ArrayList<User> to_user = findUsers(to);
        for(User u : to_user){
//            inContact(from ,u.getEmail());
            u.receiveMessage(from, message);
            from_user.sendMessage(u.getEmail(), message);
        }
    }

    /**
     * Returns a list of user objects
     * @param emails the emails of the user we wish to find
     * @return an array list of user objects
     */
    public ArrayList<User> findUsers(ArrayList<String> emails){
        ArrayList<User> user_obj = new ArrayList<User>();
        for(String i : emails){
            user_obj.add(this.findUser(i));
        }
        return user_obj;
    }

    /**
     * Identifies the Entities.User from the provided email address
     * @param  email the email of the user whose object location we want
     * @return Entities.User associated with provided email address
     */
    public User findUser(String email){
        for(User user: users){
            if(user.getEmail().equals(email)){
                return user;
            }
        }
        return null;
    }

    /**
     * Verifies if there is a Entities.User stored who is associated with the provided email address
     * @param email the email of the user whom we wish to know exists or not
     * @return true if the Entities.User exists
     */
    public boolean checkUserExists(String email) {
        return this.email.contains(email);
    }

    public ArrayList<String> getEmail() {
        return email;
    }

    /**
     * Returns the list of users that exist in the system
     * @param from is the Arraylist of emails whose existence we wish to check
     * @return
     */
    public ArrayList<String> checkUsers(ArrayList<String> from){
        ArrayList<String> u = new ArrayList<String>();
        for(String i: from){
            if (checkUserExists(i)){
                u.add(i);
            }
        }
        return u;
    }

//    private void inContact(String current, String email){
//        ArrayList<String> u = findUser(current).getContacts();
//        if (u.contains(email)){
//            findUser(current).addContact(email);
//            findUser(email).addContact(current);
//        }
//    }

    private void addListContacts(User attendee, ArrayList<String> contacts){
        if (contacts.size()!=0){
            for(String i: contacts){
                if (!(attendee.getContacts().contains(i)) && findUser(i) != null){
                    attendee.addContact(i);
                    findUser(i).addContact(attendee.getEmail());
                }
            }
        }

    }

    public ArrayList<String> checkReadM(String user, String contact){
        User u = findUser(user);
        ArrayList<String> messages = new ArrayList<>();
        for(String message: u.getMessagesReceived().get(contact)) {
            if(!message.contains("(read) - ")){
                messages.add("(Unread) - " + message);
            }else{
                messages.add(message);
            }

        }
        return messages;
    }

    public String markRead(String message){
        if(message.contains("(read) - ")){
            return message;
        }
        return "(read) - " + message.substring(11);
    }


    private void removeListContacts(Attendee attendee, ArrayList<String> contacts){
        for(String i: contacts){
            Attendee u = (Attendee) findUser(i);
            int ctr = 0;
            for(Integer t: u.getEventsAttending()){
                if(attendee.getEventsAttending().contains(t)){ctr = ctr +1;}
            }
            if (ctr == 1){
                attendee.removeContact(i);
                findUser(attendee.getEmail()).removeContact(i);
            }
        }
    }

    private void removeSpeakerContacts(Attendee attendee, User user){
        Speaker speaker = (Speaker) user;
        for(Integer i: speaker.getEventsSpeaking()){
            if (!(attendee.getEventsAttending().contains(i))){
                attendee.removeContact(speaker.getEmail());
                speaker.removeContact(attendee.getEmail());
            }
        }
    }

    /**
     * Updates the Entities.VIP 's member status depending on the total number of points they currently have
     * @param vip the Entities.VIP whose points need to be updated
     */
    public void updateMemberStatus(VIP vip){
        int totalPoints = vip.getMemberPoints();
        if (totalPoints >= 1000){
            vip.setMemberStatus("Platinum");
        } else if (totalPoints < 1000 && totalPoints >= 500){
            vip.setMemberStatus("Gold");
        } else if (totalPoints < 500 && totalPoints >= 100){
            vip.setMemberStatus("Silver");
        } else if (totalPoints < 100)
            vip.setMemberStatus("Bronze");
    }

}

