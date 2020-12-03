package UseCase;

import Entities.*;

import java.util.ArrayList;

/**
 * Manages all Users.
 *
 * @author Isha Sharma and Tanya Thaker
 * @version 1.0
 */
public class UserManager{
    public static ArrayList<User> users = new ArrayList<User>();
    public static ArrayList<String> email = new ArrayList<String>();
    public EventManager em = new EventManager(); // can we do this?
    public ConferenceManager cm = new ConferenceManager();


    /**
     * UseCase.UserManager Constructor
     */
    public UserManager(){}

    /** Allows a user to create a new account by checking if anyone with the same email id has already been registered.
     * @param name the name of the user.
     * @param password the password of this users account.
     * @param email the email of this users account.
     */
    public void addUser(String name, String email, String password, String typeOfUser) {
        UserManager.email.add(email);
        switch (typeOfUser) {
            case "organizer":
                users.add(new Organizer(name, password, email));
            case "speaker":
                users.add(new Speaker(name, password, email));
            case "attendee":
                users.add(new Attendee(name, password, email));
            case "vip":
                users.add(new VIP(name, password, email));
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
        if (UserManager.email.contains(email)){
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
     * @see ConferenceManager#addAttendeesToConference(Conference, String)
     * @see ConferenceManager#eventInConference(Integer)
     * @see EventManager#findEvent(Integer)
     */
    public boolean signUpEvent(Attendee attendee, Integer event){
        Event e = em.findEvent(event);
        if(!(cm.eventInConference(event) == null) && !attendee.getEventsAttending().contains(event)){
            cm.addAttendeesToConference(cm.eventInConference(event), attendee.getEmail());
            attendee.attendConference(cm.eventInConference(event).getName());
            return true; // signed up in a conference
        } else if (cm.eventInConference(event) == null && !attendee.getEventsAttending().contains(event) &&
                e.getAttendeeCapacity() < e.getAttendeeEmails().size()){
            attendee.attendEvent(e.getEventId());
            e.addAttendee(attendee.getEmail());
            return true; //signed up
        }
        return false; //cannot be signed up
    }

    // NEED TO FIX - Tanya
    /**
     * Cancels Entities.Attendee's registration for an Entities.Event. Checks to see that the specified Entities.Attendee is actually signed up
     * for the specified Entities.Event, before removing the Entities.Attendee from the Entities.Event.
     * @param attendee the Entities.Attendee who wants to cancel registration for an event
     * @param event the Entities.Event that the attendee would no longer like to attend
     * @see Attendee#getEventsAttending()
     * @see Attendee#removeEvent(Integer)
     * @see Attendee#getEmail()
     * @see Event#removeAttendee(String)
     * @see EventManager#findEvent(Integer)
     */
    public boolean cancelRegistrationEvent(Attendee attendee, Integer event){
        Conference c = cm.eventInConference(event);
        int ctr = 0;
        if (!(c== null)){
            for(int e: c.getEventIds()){
                if (em.findEvent(e).getAttendeeEmails().contains(attendee.getEmail())){ctr = ctr +1;}
                // counts if attendee is part of more than 1
            }
        }
        if(attendee.getEventsAttending().contains(event)){
            attendee.removeEvent(event);
            em.findEvent(event).removeAttendee(attendee.getEmail());
            if (ctr == 1){attendee.removeConference(c.getName());} // if part of only this event, remove conference
            return true; //cancelled reg in event
        }
        return false;
    }


    /**
     * Signs up Entities.Attendee for the conference
     * @param attendee the attendee who wishes to sign up for a conference
     * @param name the name of the conference
     */
    public void signUpConference(Attendee attendee, String name){
        Conference c = cm.findConference(name);
        cm.addAttendeesToConference(c, attendee.getEmail());
        attendee.attendConference(name);
    }

    /**
     * Signs up Entities.Attendee for the conference
     * @param attendee the attendee who wishes to cancel registration  for a conference
     * @param name the name of the conference
     */
    public void cancelRegistrationConference(Attendee attendee, String name){
        Conference c = cm.findConference(name);
        cm.removeAttendeeConference(c,attendee.getEmail());
        attendee.removeConference(c.getName());
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
            inContact(from, u1.getEmail());
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
            inContact(from, i.getEmail());
            findUser(from).sendMessage(i.getEmail(), message);
            i.receiveMessage(from, message);
        }
    }


    /// the only thing this method does is send messages. It doesn't check anything
    private void attendeeMessage(String from, ArrayList<String> to, String message){
        User from_user = findUser(from);
        ArrayList<User> to_user = findUsers(to);
        for(User u : to_user){
            inContact(from ,u.getEmail());
            u.receiveMessage(from, message);
            from_user.sendMessage(u.getEmail(), message);
        }
    }


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
        int i;
        i = UserManager.email.indexOf(email);
        return UserManager.users.get(i);
    }

    /**
     * Verifies if there is a Entities.User stored who is associated with the provided email address
     * @param email the email of the user whom we wish to know exists or not
     * @return true if the Entities.User exists
     */
    public boolean checkUserExists(String email) {
        return UserManager.email.contains(email);
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

    private void inContact(String current, String email){
        ArrayList<String> u = findUser(current).getContacts();
        if (u.contains(email)){
            findUser(current).addContact(email);
            findUser(email).addContact(current);
        }
    }
}
