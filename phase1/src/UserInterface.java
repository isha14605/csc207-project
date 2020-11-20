import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

class UserInterface {

    public static void OrganizerInterface(EventController eventController, UserManager um,
                                          MessagingSystem ms, String email) throws IOException {
        Organizer organizer = (Organizer) um.findUser(email);

        boolean on_page = true;
        while (on_page) {
            EventManager em = eventController.em;
            RoomManager rm = eventController.rm;
            Scanner userInput = new Scanner(System.in);  // Create a Scanner object
            System.out.println("==============Organizer Interface==================" +
                    "\n -Add Event- enter AE-" +
                    "\n -Create Room- enter CR-" +
                    "\n -Create Speaker- enter CS-" +
                    "\n -Event Options- enter EO-" +
                    "\n -View Events- enter VE-" +
                    "\n -View Rooms- enter VR-" +
                    "\n -Inbox- enter IB" +
                    "\n -Exit- enter exit");

            String option = userInput.nextLine();  // Read user input
            switch (option) {

                case "AE":
                    System.out.println("====Event Creator====");
                    System.out.println("What is the name of the event?");
                    String name = userInput.nextLine();
                    System.out.println("What is the description?");
                    String description = userInput.nextLine();
                    System.out.println("When does the event start?");
                    String start = userInput.nextLine();
                    System.out.println("When does the event end?");
                    String end = userInput.nextLine();
                    System.out.println("What date is the event?");
                    String date = userInput.nextLine();

                    eventController.add_event(name, description, start, end, date);
                    break;

                case "CR":
                    System.out.println("====Room Creator====");

                    System.out.println("What is the name of the room?");
                    String room_name = userInput.nextLine();
                    System.out.println("How many people can the room hold?");
                    int capacity = userInput.nextInt();
                    System.out.println("When does the room open?");
                    String open = userInput.next();
                    System.out.println("When does the room close?");
                    String close = userInput.next();

                    eventController.add_room(room_name, capacity, open, close);

                    break;

                case "CS":
                    System.out.println("====Speaker Creator====");

                    System.out.println("Enter the speaker's name.");
                    String speakerName = userInput.nextLine();
                    System.out.println("Enter the speaker's email.");
                    String speakerEmail = userInput.nextLine();
                    System.out.println("Enter the speaker's password.");
                    String speakerPassword = userInput.nextLine();

                    um.addUser(speakerName, speakerEmail, speakerPassword, "speaker");

                    break;

                case "IB":
                    System.out.println("====Messages Inbox====" +
                            "\n -Send individual message- enter IM" +
                            "\n -Send group message- enter GM" +
                            "\n -Add contact- enter AD" +
                            "\n -View messaging history- enter MH" +
                            "\n -View contacts- enter CO" +
                            "\n -Exit- enter exit");

                    String userOption = userInput.next();
                    switch(userOption) {
                        case "IM":
                            System.out.println("Enter the email of the contact you would like to message.");
                            String contactEmail = userInput.nextLine();
                            if (um.checkUserExists(contactEmail)) {
                                if (organizer.getContacts().contains(um.findUser(contactEmail))) {
                                    System.out.println("Enter the message you would like to send.");
                                    String message = userInput.nextLine();
                                    ms.sendAttendeeMessage(organizer, um.findUser(contactEmail), message);
                                } else {
                                    System.out.println("Error: This user is not in your contacts list.");
                                }
                            } else {
                                System.out.println("Error: This user does not exist.");
                            }

                        case "GM":
                            System.out.println("Would you like to send a message to all Attendees or to all Speakers?" +
                                    "\n Enter Attendee for Attendees or Speaker for Speakers");
                            String choice = userInput.nextLine();
                            System.out.println("Enter the event id relevant to this message.");
                            int event = userInput.nextInt();
                            System.out.println("Enter the message you would like to send.");
                            String message = userInput.nextLine();
                            ms.sendMessageOrganizer(organizer, choice, event,  message);

                        case "AD":
                            System.out.println("Enter the email of the contact you would like to add.");
                            String newContact = userInput.nextLine();

                            if (um.checkUserExists(newContact)) {
                                if (!organizer.getContacts().contains(um.findUser(newContact))) {
                                    organizer.addContact(um.findUser(email));
                                    System.out.println("Contact added successfully!");
                                } else {
                                    System.out.println("Error: This contact is already in your list of contacts.");
                                }
                            } else {
                                System.out.println("Error: This user does not exist.");
                            }

                            break;

                        case "MH":
                            System.out.println("Enter the email of the contact you would like to review your " +
                                    "message history with.");
                            String contact = userInput.nextLine();

                            if (um.checkUserExists(contact) &&
                                    organizer.getContacts().contains(um.findUser(contact))) {
                                ArrayList<String> messagesReceived =
                                        organizer.getMessagesReceived().get(um.findUser(contact));
                                if (!(messagesReceived.size() == 0)) {
                                    System.out.println("Enter the number of messages you would like to see.");
                                    String num = userInput.next();
                                    while (Integer.parseInt(num) > messagesReceived.size()) {
                                        System.out.println("Enter the number of messages you would like to see.");
                                        num = userInput.next();
                                    }
                                    for (int i = messagesReceived.size() - Integer.parseInt(num);
                                         i < messagesReceived.size(); i++) {
                                        System.out.println(messagesReceived.get(i));
                                    }
                                } else {
                                    System.out.println("You have no messages from this person.");
                                }
                            } else {
                                System.out.println("Error: This user does not exist or is not in your contacts list.");
                            }

                            break;

                        case "CO":
                            if (!(organizer.contacts.size() == 0)) {
                                System.out.println("====Contacts====");
                                for (User c: organizer.contacts) {
                                    System.out.println(c.name);
                                }
                            } else {
                                System.out.println("You do not have any contacts.");
                            }
                            break;

                        case "exit":
                            on_page = false;
                    }

                case "VE":

                    em.print_events();

                    if (eventController.get_events().size() == 0) {
                        System.out.println("No events are scheduled.");
                    }
                    break;
                case "VR":
                    RoomManager roomManager = new RoomManager();
                    for (Room room : eventController.get_rooms()) {
                        System.out.println(roomManager.roomToString(room));
                    }
                    if (eventController.get_rooms().size() == 0) {
                        System.out.println("No rooms have been created.");
                    }
                    break;

                case "EO":
                    System.out.println("====Event Options====");
                    if (eventController.get_events().size() == 0) {
                        System.out.println("no events schedule to do actions! \n");
                    } else {
                        System.out.println("-Schedule Speaker- SS");
                        System.out.println("-Schedule Room- SR");
                        System.out.println("-Create Talk- enter CT");
                        System.out.println("-Add Talk- AT");
                        String event_options = userInput.nextLine();
                        switch (event_options) {
                            case "CT":
                                System.out.println("====Talk Creator====");
                                System.out.println("What Event Would you like to add talk to");
                                em.print_events();
                                int event_id = userInput.nextInt();
                                if (em.find_event(event_id).getEventRoom() == null) {
                                    System.out.println("Event needs to be scheduled a room before" +
                                            "talks can be added");
                                } else {
                                    System.out.println("When does the talk start");
                                    start = userInput.next();
                                    System.out.println("When does the talk end");
                                    end = userInput.next();
                                    eventController.add_talk(start, end, event_id);

                                }
                                break;

                            case "SS":
                                System.out.println("What event would you like to schedule speaker for?");
                                em.print_events();
                                int eventID = userInput.nextInt();
                                System.out.println("What talk in this event would you like to schedule speaker for?");
                                int talkID = userInput.nextInt();
                                System.out.println("Enter the email of the speaker you want to schedule.");
                                String speaker = userInput.next();
                                if (!(em.find_event(eventID).getTalks().size() == 0)) {
                                    for (Talk talk: em.find_event(eventID).getTalks()) { // Check that event has talk
                                        if (talk.getId() == talkID && um.checkUserExists(speaker)) {
                                            // Check that speaker can be scheduled
                                            boolean check = eventController.schedule_speaker((Speaker) um.findUser(speaker), talk, em.find_event(eventID));
                                            if (check) {
                                                System.out.println("Speaker has been scheduled!");
                                            } else {
                                                System.out.println("Error. This speaker cannot be scheduled for this talk.");
                                            }
                                        } else {
                                            System.out.println("Error. This talk does not exist or speaker does not exist.");
                                        }
                                    }
                                } else {
                                    System.out.println("There are no talks for this event.");
                                }
                                break;

                            case "SR":
                                if (eventController.get_rooms().size() == 0) {
                                    System.out.println("No rooms to perform actions to! \n");
                                } else {
                                    System.out.println("What event do you want to schedule room for?");

                                    em.print_events();

                                    int id = userInput.nextInt();
                                    if(em.event_exist(id)){
                                        System.out.println("What room what do you want to schedule?");
                                        for (Room room : eventController.get_rooms()) {
                                            System.out.println(rm.roomToString(room));
                                        }
                                        room_name = userInput.next();

                                        eventController.schedule_room(room_name, id);
                                    }else{
                                        System.out.println("Event does not exist.");
                                    }
                                }
                                break;

                            case "AT":
                                System.out.println("What event are you adding a talk to?");
                                em.print_events();
                                int event = userInput.nextInt();
                                System.out.println("When does the talk start?");
                                start = userInput.next();
                                System.out.println("When does the talk end?");
                                end = userInput.next();
                                if(eventController.add_talk(start,end,event)){
                                    System.out.println("Talk was added to Event " + event);
                                }else{
                                    System.out.println("Talk was not added.");
                                }
                                break;
                        }
                    }
                    break;
                case "save":
                    eventController.save();
                    break;

                case "exit":
                    on_page = false;

            }
        }

    }


    public static void AttendeeInterface(SignUpSystem signUpSystem, EventManager eventManager, EventController ec,
                                         UserManager um, MessagingSystem ms, String email) {
        Attendee attendee = (Attendee) um.findUser(email);

        boolean on_page = true;
        while (on_page) {
            Scanner userInput = new Scanner(System.in);  // Create a Scanner object
            System.out.println("==============Attendee Interface==================" +
                    "\n - Browse all events - type BE" +
                    "\n - Signup for events - type SU" +
                    "\n - View all events you're attending/cancel attendance at an event - type VC" +
                    "\n - Inbox for messages - type IB" +
                    "\n - Exit - type Exit");
            String option = userInput.next();  // Read user input
            switch (option) {
                case "BE":
                    if (ec.get_events().size() == 0) {
                        System.out.println("===== Event Browser =====" +
                                "\nNo events have been scheduled. Cannot perform this action.");
                    } else {
                        System.out.println("===== Event Browser =====");
                        System.out.println("Which date would you like to see events for?");
                        String date = userInput.next();
                        LocalDate dateF = eventManager.date_formatting_date(date);
//                        System.out.println("What start time would you like to see events for?");
//                        String start = userInput.next();
//                        LocalTime startF = eventManager.date_formatting_time(start);
//                        System.out.println("What end time would you like to see events for?");
//                        String end = userInput.next();
//                      System.out.println("And end time");
//                        LocalTime endF = eventManager.date_formatting_time(end);
                        ArrayList<Event> browsed = signUpSystem.browseEvents(ec.em, dateF);
                        System.out.println(browsed.size());
                        for (Event event : browsed) {
                            System.out.println("It sorta works");
                            System.out.println(eventManager.eventToString(event));
                        }
                    }
                    break;

                case "SU":
                    if (ec.get_events().size() == 0) {
                        System.out.println("===== Event Browser =====" +
                                "\nNo events have been scheduled. Cannot perform this action.");
                    } else {
                        System.out.println("===== Event Sign Up =====");

                        for (Event scheduled : ec.get_events()) {
                            System.out.println(eventManager.eventToString(scheduled));
                        }
                        System.out.println("Enter the event id of the event you want to join.");
                        int event_id = userInput.nextInt();
                        //Issue with getEvents() in EM, needs to be fixed
                        while (event_id > ec.em.getEvents().size() || event_id < 1){
                            System.out.println("Invalid id! Please try again.");
                            event_id = userInput.nextInt();
                        }
                        Event event = ec.em.find_event(event_id);
                        if(event.getEventRoom() == null){
                            System.out.println("Sorry, the event hasn't been assigned a room. Unable to join.\n");
                            break;
                        } else {
                            boolean flag = signUpSystem.signUpEvent(attendee, event);
                            if (flag){
                                System.out.println("You've been registered!");
                            } else {
                                System.out.println("This event no longer has any space.");
                            }
                        }
                    }
                    break;
                case "VC":
                    if (attendee.getEventsAttending().size() == 0) {
                        System.out.println("You are not signed up for any events.");
                    } else {
                        System.out.println("Here is a list of events you are signed up for:");
                        for (Event attending: attendee.getEventsAttending()) {
                            System.out.println("Event Name: " + attending.getName() + " Event ID: " + attending.getEventId());
                        }
                        System.out.println("Would you like to cancel attendance at an event? Type Yes or No.");
                        String toCancel = userInput.next();
                        boolean cancelled = false;
                        if (toCancel.equals("Yes")) {
                            System.out.println("What event would you like to cancel attendance for? Type the ID.");
                            String eventID = userInput.next();
                            ArrayList<Event> attending_copy = new ArrayList<Event>(attendee.getEventsAttending());
                            for (Event attending: attending_copy){
                                if (attending.getEventId() == Integer.parseInt(eventID)) {
                                    um.cancelRegistration(attendee, attending);
                                    cancelled = true;
                                } else {
                                    cancelled = false;
                                }
                            }
                            if (cancelled) {
                                System.out.println("Attendance cancelled for this event.");
                            } else {
                                System.out.println("You were never signed up for this event.");
                            }
                        }
                    }
                    break;
                case "IB":
                    // Five options for Attendee
                    System.out.println("============== Inbox ==================" +
                            "\n - Send a message - SM" +
                            "\n - Add a contact - type AD" +
                            "\n - View messaging history - type MH" +
                            "\n - View contacts - type CO" +
                            "\n - Exit - type Exit"); // ask about Exit
                    String options = userInput.next();
                    switch (options) {
                        // Send a message - SM
                        case "SM":
                            if (attendee.contacts.size() == 0) {
                                System.out.println("You do not have any contacts to message. You must add them first.");
                            } else {
                                System.out.println("Enter the email of the person you would like to message.");
                                String receiver_email = userInput.next();
                                System.out.println("Enter the message you would like to send.");
                                String message = userInput.next();
                                // Check to see if the person they want to message exists in the system
                                if (um.checkUserExists(receiver_email)) {
                                    // Check to see if they are allowed to message the person based on the type of User,
                                    // by using the message method from UserManager
                                    // If the person they want to message is not in their contacts, they are added via
                                    // the attendeeMessage method inside the message method in UserManager
                                    // The attendeeMessage method also handles appending the sent message to the
                                    // appropriate lists of the sender and receiver
                                    boolean flag = ms.sendAttendeeMessage(attendee, um.findUser(receiver_email), message);
                                    if (flag){
                                        System.out.println("Message sent!");
                                    } else {
                                        System.out.println("Error. You do not have permission to message this person.");
                                    }
                                } else { // Executed when the person that the Attendee wants to message does not exist
                                    System.out.println("Error. This person does not exist in our records.");
                                }
                            }
                            break;
                        // Add a contact - AD
                        case "AD":
                            System.out.println("Enter the email of the person you would like to add as a contact.");
                            String newContact = userInput.next();
                            // Check to see if the person exists
                            if (um.checkUserExists(newContact)) {
                                // Checks to see that they can add this type of person and that person they want to add
                                // is not already in their contacts list
                                boolean is_Attendee = (um.findUser(newContact).userType() == 'A');
                                boolean is_Speaker = (um.findUser(newContact).userType() == 'S');
                                if ((is_Attendee || is_Speaker) && !attendee.contacts.contains(um.findUser(newContact))) {
                                    attendee.addContact(um.findUser(newContact)); //adds the contact to their list
                                    System.out.println("Added contact successfully.");
                                } else {
                                    System.out.println("Error. You do not have permission to add this person or " +
                                            "this person is already in your contacts.");
                                }
                            } else { // Executed when the person that the Attendee wants to add does not exist
                                System.out.println("Error. This person does not exist in our records.");
                            }
                            break;
                        // View messaging history - MH
                        case "MH":
                            System.out.println("Enter the email of the contact to view your message history with them.");
                            String contact = userInput.next();
                            // Check conditions similar to above two methods and print appropriate messages if errors
                            if (um.checkUserExists(contact) && attendee.getContacts().contains(um.findUser(contact))) {
                                ArrayList<String> messages_received = attendee.getMessagesReceived().get(um.findUser(contact));
                                System.out.println("Enter the number of messages you would like to see.");
                                String num = userInput.next();
                                if (!(messages_received.size() == 0)) {
                                    while (Integer.parseInt(num) > messages_received.size()) {
                                        System.out.println("Enter the number of messages you would like to see.");
                                        num = userInput.next();
                                    }
                                    for (int i = messages_received.size() - Integer.parseInt(num); i < messages_received.size(); i++) {
                                        System.out.println(messages_received.get(i));
                                    }
                                } else {
                                    System.out.println("You have no messages from this person.");
                                }
                            } else { // Executed when the person that the Attendee wants to add does not exist
                                System.out.println("Error. This person does not exist in our records or they are not in your contact list.");
                            }
                            break;
                        // View contacts - CO    
                        case "CO":
                            if (!(attendee.contacts.size() == 0)) {
                                System.out.println("============== Your Contacts ==================");
                                // Loop through their contacts list and print out the names of each of their contacts
                                for (User c: attendee.contacts) {
                                    System.out.println(c.name);
                                }
                            } else {
                                System.out.println("You do not have any contacts.");
                            }
                            break;
                    }
                    break;

                case "Exit":
                    on_page = false;

            }
        }
    }

    public static void SpeakerInterface(SignUpSystem signUpSystem, EventManager eventManager, EventController ec,
                                        UserManager um, MessagingSystem ms, String email) {
        UserManager userManager = new UserManager();

        Speaker speaker = (Speaker) um.findUser(email);
        boolean on_page = true;

        while (on_page) {
            Scanner userInput = new Scanner(System.in);  // Scanner
            System.out.println("==============Speaker Interface==================" +
                    "\n -Browse My Talks- type BMT" +
                    "\n -Inbox- type IB" +
                    "\n -Exit- type Exit");
            String option = userInput.next();
            switch (option) {
                case "BMT":
                    System.out.println("These are the talks that you are speaking at:");
                    if (speaker.getTalksSpeaking().size() == 0) {
                        System.out.println("You are currently not scheduled to speak at any talks.");
                    } else {
                        for (int i = 0; i < speaker.getTalksSpeaking().size(); i++) {
                            System.out.println(speaker.getTalksSpeaking().get(i));
                        }
                    }
                    break;
                case "IB":
                    System.out.println("============== Inbox ==================" +
                            "\n - Send a Message to Attendees - enter SM" +
                            "\n - View Messaging History and Respond to Individual Attendee- enter VMH" +
                            "\n - Exit - enter E");
                    String inboxOption = userInput.next();
                    if ("SM".equals(inboxOption)) {
                        System.out.println("You are currently scheduled to talk at " + speaker.getTalksSpeaking().size() + "talks.");
                        System.out.println("How many of these talks' attendees would you like to message?");
                        int numberOfTalks = userInput.nextInt();

                        while (numberOfTalks > speaker.getTalksSpeaking().size()) {
                            System.out.println("You are not speaking at this many talks. Please re-enter.");
                            numberOfTalks = userInput.nextInt();
                        }

                        // Allows speaker to enter all the talks that they want to send to a message to the attendees of.
                        System.out.println("On a new line for each event, please enter ids of all the events that you " +
                                "want to send messages to the attendees of.");
                        ArrayList<String> toSendMessagesTo = new ArrayList<>(numberOfTalks); // Creates a new list
                        for (int i = 0; i < numberOfTalks; i++) {
                            String a = userInput.nextLine(); // stores the input to be added
                            toSendMessagesTo.add(a); // adds the talk to the list
                        }
                        ArrayList<Event> events_at = new ArrayList<Event>();

                        for (Talk t : speaker.getTalksSpeaking()) {
                            if (!(events_at.contains(t.getEvent()))) {
                                events_at.add(t.getEvent());
                            }
                        }
                        ArrayList<Event> final_events = new ArrayList<Event>();

                        for (String s : toSendMessagesTo) {
                            if (events_at.contains(eventManager.find_event(Integer.parseInt(s)))) {
                                final_events.add((eventManager.find_event(Integer.parseInt(s))));
                            }
                        }

                        System.out.println("Please enter the message you would like to send to all the attendees" +
                                " of these selected talks.");
                        String messageToSend = userInput.nextLine(); // Takes the message the speaker wants to send
                        ms.sendMessageSpeaker(speaker, final_events, messageToSend); // calls the method from UserManager to send the messages
                    }
                            break;
                        case "VMH":
                            boolean hasMessagesFromContact = false;
                            System.out.println("Enter an email to view your message history with them.");
                            String emailOfContact = userInput.next();

                            // Check if this user exists in the system
                            if (userManager.checkUserExists(email)){
                                ArrayList<String> messages_received = speaker.getMessagesReceived().get(um.findUser(emailOfContact));

                                System.out.println("Enter the number of messages you would like to see.");
                                String numMessages = userInput.next();

                                if (!(messages_received.size() == 0)) {
                                    while (Integer.parseInt(numMessages) > messages_received.size()) {
                                        System.out.println("Enter the number of messages you would like to see.");
                                        numMessages = userInput.next();
                                    }
                                    for (int i = messages_received.size() - Integer.parseInt(numMessages); i < messages_received.size(); i++) {
                                        System.out.println(messages_received.get(i));
                                        hasMessagesFromContact = true;
                                    }
                                } else {
                                    System.out.println("You have no messages from this person.");
                                }
                            } else {
                                System.out.println("Error. This person does not exist in our records.");
                            }

                            if (hasMessagesFromContact){
                                System.out.println("Would you like to respond to this Attendee? Yes or No.");
                                String respondDecision = userInput.next();
                                switch (respondDecision){
                                    case "Yes":
                                        System.out.println("Please enter your response.");
                                        String messageResponse = userInput.next();
                                        ms.sendAttendeeMessage(speaker, um.findUser(email), messageResponse);
                                        break;
                                    case "No":
                                        on_page = false;
                                }
                            }

                            break;
                        case "E":
                            on_page = false;
                case "Exit":
                    on_page = false;
            }
        }
    }

    public static void LoginInterface() throws ClassNotFoundException, IOException {

        UserManager userManager = new UserManager();
        LoginController loginSystem = new LoginController();
        EventController ec = new EventController();
        SignUpSystem su = new SignUpSystem();
        MessagingSystem ms = new MessagingSystem();
        Scanner userInput = new Scanner(System.in);
        EventManager em = new EventManager();
        boolean signed_in = false;

        while (!signed_in) {
            System.out.println("|===== Phase 1 login =====|");
            System.out.println("Enter your Email");
            String email = userInput.next();
            System.out.println("Enter your Password");
            String password = userInput.next();
            signed_in = loginSystem.checkLogIn(email, password);
            if (!signed_in) {
                System.out.println("Invalid Login Credentials");
            }

            if (signed_in) {
                System.out.println("Logged In....");
                boolean homeScreen = true;
                while (homeScreen) {
                    System.out.println("=============== Phase 1 System ===============");
                    char usertype = userManager.findUser(email).userType();
                    if (usertype == 'O') {
                        System.out.println("-Organiser Options- OO");
                    } else if (usertype == 'A') {
                        System.out.println("-Attendee Options - AO");
                    } else if (usertype == 'S') {
                        System.out.println("-Speaker Options - SO");
                    }
                    System.out.println("-Log Out- LO");
                    String option = userInput.next();

                    switch (option) {
                        case "AO":
                            AttendeeInterface(su,em,ec, userManager, ms, email);
                            break;

                        case "OO":
                            OrganizerInterface(ec, userManager, ms, email);
                            break;

                        case "SO":
                            SpeakerInterface(su,em,ec, userManager, ms, email);
                            break;

                        case "LO":
                            signed_in = true;
                            homeScreen = false;
                    }
                }
                signed_in = false;
            }
        }
    }



    public static class EventPlannerSystem {
        public void run() throws IOException, ClassNotFoundException {
            LoginInterface();
        }

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        EventPlannerSystem e = new EventPlannerSystem();
        e.run();

    }
}



