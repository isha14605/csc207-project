//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//class UserInterface {
//
//    public static void OrganizerInterface(EventController eventController, UseCase.UserManager um,
//                                          Controllers.MessagingSystem ms, String email) throws IOException {
//        Entities.Organizer organizer = (Entities.Organizer) um.findUser(email);
//
//        boolean on_page = true;
//        while (on_page) {
//            UseCase.EventManager em = eventController.em;
//            UseCase.RoomManager rm = eventController.rm;
//            Scanner userInput = new Scanner(System.in);  // Create a Scanner object
//            System.out.println("==============Entities.Organizer Interface==================" +
//                    "\n -Add Entities.Event- enter AE-" +
//                    "\n -Create Entities.Room- enter CR-" +
////                  "\n -Create Entities.Speaker- enter CS-" +
//                    "\n -Entities.Event Options- enter EO-" +
//                    "\n -View Events- enter VE-" +
//                    "\n -View Rooms- enter VR-" +
//                    "\n -Inbox- enter IB" +
//                    "\n -Exit- enter exit");
//
//            String option = userInput.nextLine();  // Read user input
//            switch (option) {
//
//                case "AE":
//                    System.out.println("====Entities.Event Creator====");
//                    System.out.println("What is the name of the event?");
//                    String name = userInput.nextLine();
//                    System.out.println("What is the description?");
//                    String description = userInput.nextLine();
//                    System.out.println("When does the event start?");
//                    String start = userInput.nextLine();
//                    System.out.println("When does the event end?");
//                    String end = userInput.nextLine();
//                    System.out.println("What date is the event?");
//                    String date = userInput.nextLine();
//
//                    eventController.add_event(name, description, start, end, date);
//                    break;
//
//                case "CR":
//                    System.out.println("====Entities.Room Creator====");
//
//                    System.out.println("What is the name of the room?");
//                    String room_name = userInput.nextLine();
//                    System.out.println("How many people can the room hold?");
//                    int capacity = userInput.nextInt();
//                    System.out.println("When does the room open?");
//                    String open = userInput.next();
//                    System.out.println("When does the room close?");
//                    String close = userInput.next();
//
//                    eventController.add_room(room_name, capacity, open, close);
//
//                    break;
//
//
//                case "IB":
//                    System.out.println("====Messages Inbox====" +
//                            "\n -Send individual message- enter IM" +
//                            "\n -Send group message- enter GM" +
//                            "\n -Add contact- enter AD" +
//                            "\n -View messaging history- enter MH" +
//                            "\n -View contacts- enter CO" +
//                            "\n -Exit- enter exit");
//
//                    String userOption = userInput.next();
//                    switch(userOption) {
//                        case "IM":
//                            System.out.println("Enter the email of the contact you would like to message.");
//                            String contactEmail = userInput.next();
//                            if (um.checkUserExists(contactEmail)) {
//                                if (organizer.getContacts().contains(um.findUser(contactEmail))) {
//                                    System.out.println("Enter the message you would like to send.");
//                                    String message = userInput.next();
//                                    ms.sendAttendeeMessage(organizer, um.findUser(contactEmail), message);
//                                } else {
//                                    System.out.println("Error: This user is not in your contacts list.");
//                                }
//                            } else {
//                                System.out.println("Error: This user does not exist.");
//                            }
//                            break;
//
//                        case "GM":
//                            System.out.println("Would you like to send a message to all Attendees or to all Speakers?" +
//                                    "\n Enter Entities.Attendee for Attendees or Entities.Speaker for Speakers");
//                            String choice = userInput.next();
//                            System.out.println("Enter the event id relevant to this message.");
//                            int event = userInput.nextInt();
//                            System.out.println("Enter the message you would like to send.");
//                            String message = userInput.next();
//                            ms.sendMessageOrganizer(organizer, choice, event,  message);
//                            break;
//
//                        case "AD":
//                            System.out.println("Enter the email of the contact you would like to add.");
//                            String newContact = userInput.next();
//
//                            if (um.checkUserExists(newContact)) {
//                                if (!organizer.getContacts().contains(um.findUser(newContact))) {
//                                    organizer.addContact(um.findUser(email));
//                                    System.out.println("Contact added successfully!");
//                                } else {
//                                    System.out.println("Error: This contact is already in your list of contacts.");
//                                }
//                            } else {
//                                System.out.println("Error: This user does not exist.");
//                            }
//
//                            break;
//
//                        case "MH":
//                            System.out.println("Enter the email of the contact you would like to review your " +
//                                    "message history with.");
//                            String contact = userInput.next();
//
//                            if (um.checkUserExists(contact) &&
//                                    organizer.getContacts().contains(um.findUser(contact))) {
//                                ArrayList<String> messagesReceived =
//                                        organizer.getMessagesReceived().get(um.findUser(contact));
//                                if (!(messagesReceived.size() == 0)) {
//                                    System.out.println("Enter the number of messages you would like to see.");
//                                    String num = userInput.next();
//                                    while (Integer.parseInt(num) > messagesReceived.size()) {
//                                        System.out.println("Enter the number of messages you would like to see.");
//                                        num = userInput.next();
//                                    }
//                                    for (int i = messagesReceived.size() - Integer.parseInt(num);
//                                         i < messagesReceived.size(); i++) {
//                                        System.out.println(messagesReceived.get(i));
//                                    }
//                                } else {
//                                    System.out.println("You have no messages from this person.");
//                                }
//                            } else {
//                                System.out.println("Error: This user does not exist or is not in your contacts list.");
//                            }
//
//                            break;
//
//                        case "CO":
//                            if (!(organizer.contacts.size() == 0)) {
//                                System.out.println("====Contacts====");
//                                for (Entities.User c: organizer.contacts) {
//                                    System.out.println(c.name);
//                                }
//                            } else {
//                                System.out.println("You do not have any contacts.");
//                            }
//                            break;
//
//                        case "Exit":
//                            on_page = false;
//
//                    }
//                    break;
//
//                case "VE":
//
//                    em.print_events();
//
//                    if (eventController.get_events().size() == 0) {
//                        System.out.println("No events are scheduled.");
//                    }
//                    break;
//                case "VR":
//                    UseCase.RoomManager roomManager = new UseCase.RoomManager();
//                    for (Entities.Room room : eventController.get_rooms()) {
//                        System.out.println(roomManager.roomToString(room));
//                    }
//                    if (eventController.get_rooms().size() == 0) {
//                        System.out.println("No rooms have been created.");
//                    }
//                    break;
//
//                case "EO":
//                    System.out.println("====Entities.Event Options====");
//                    if (eventController.get_events().size() == 0) {
//                        System.out.println("no events schedule to do actions! \n");
//                    } else {
//                        System.out.println("-Schedule Entities.Speaker- SS");
//                        System.out.println("-Schedule Entities.Room- SR");
////                        System.out.println("-Create Entities.Talk- enter CT");
//                        System.out.println("-Add Entities.Talk- AT");
//                        String event_options = userInput.nextLine();
//                        switch (event_options) {
////                            case "CT":
////                                System.out.println("====Entities.Talk Creator====");
////                                System.out.println("What Entities.Event Would you like to add talk to");
////                                em.print_events();
////                                int event_id = userInput.nextInt();
////                                if (em.find_event(event_id).getEventRoom() == null) {
////                                    System.out.println("Entities.Event needs to be scheduled a room before" +
////                                            "talks can be added");
////                                } else {
////                                    System.out.println("When does the talk start");
////                                    start = userInput.next();
////                                    System.out.println("When does the talk end");
////                                    end = userInput.next();
////                                    eventController.add_talk(start, end, event_id);
////
////                                }
////                                break;
//
//                            case "SS":
//                                System.out.println("What event would you like to schedule speaker for?");
//                                em.print_events();
//                                int eventID = userInput.nextInt();
//                                System.out.println("What talk in this event would you like to schedule speaker for?");
//                                int talkID = userInput.nextInt();
//                                System.out.println("Enter the email of the speaker you want to schedule.");
//                                String speaker = userInput.next();
//                                boolean flag = false;
//                                if (!(um.checkUserExists(speaker))){
//                                    System.out.println("No such speaker found!");
//                                } else{
//                                    if (!(em.find_event(eventID).getTalks().size() == 0)) {
//                                        for (Entities.Talk talk: em.find_event(eventID).getTalks()) { // Check that event has talk
//                                            if (talk.getId() == talkID && um.checkUserExists(speaker)) {
//                                                // Check that speaker can be scheduled
//                                                boolean check = eventController.schedule_speaker((Entities.Speaker) um.findUser(speaker), talk, em.find_event(eventID));
//                                                if (check) {
//                                                    System.out.println("Entities.Speaker has been scheduled!");
//                                                    flag = true;
//
//                                                } else {
//                                                    System.out.println("Error. This speaker cannot be scheduled for this talk.");
//                                                    flag = true;
//                                                }
//                                                break;
//                                            }
//                                        }
//                                        if (!flag){
//                                            System.out.println("Error. This talk does not exist.");
//                                        }
//                                    } else {
//                                        System.out.println("There are no talks for this event.");
//                                    }
//                                }
//
//                                  break;
//
//                            case "SR":
//                                if (eventController.get_rooms().size() == 0) {
//                                    System.out.println("No rooms to perform actions to! \n");
//                                } else {
//                                    System.out.println("What event do you want to schedule room for?");
//
//                                    em.print_events();
//
//                                    int id = userInput.nextInt();
//                                    if(em.event_exist(id)){
//                                        System.out.println("What room what do you want to schedule?");
//                                        for (Entities.Room room : eventController.get_rooms()) {
//                                            System.out.println(rm.roomToString(room));
//                                        }
//                                        room_name = userInput.next();
//
//                                        eventController.schedule_room(room_name, id);
//                                    }else{
//                                        System.out.println("Entities.Event does not exist.");
//                                    }
//                                }
//                                break;
//
//                            case "AT":
//                                System.out.println("What event are you adding a talk to?");
//                                em.print_events();
//                                int event = userInput.nextInt();
//                                System.out.println("When does the talk start?");
//                                start = userInput.next();
//                                System.out.println("When does the talk end?");
//                                end = userInput.next();
//                                if(eventController.add_talk(start,end,event)){
//                                    System.out.println("Entities.Talk was added to Entities.Event " + event);
//                                }else{
//                                    System.out.println("Entities.Talk was not added.");
//                                }
//                                break;
//                        }
//                    }
//                    break;
//                case "save":
//                    eventController.save();
//                    break;
//
//                case "Exit":
//                    on_page = false;
//
//            }
//        }
//
//    }
//
//
//    public static void AttendeeInterface(Controllers.SignUpSystem signUpSystem, UseCase.EventManager eventManager, EventController ec,
//                                         UseCase.UserManager um, Controllers.MessagingSystem ms, String email) {
//        Entities.Attendee attendee = (Entities.Attendee) um.findUser(email);
//
//        boolean on_page = true;
//        while (on_page) {
//            Scanner userInput = new Scanner(System.in);  // Create a Scanner object
//            System.out.println("==============Entities.Attendee Interface==================" +
//                    "\n - Browse all events - type BE" +
//                    "\n - Signup for events - type SU" +
//                    "\n - View all events you're attending/cancel attendance at an event - type VC" +
//                    "\n - Inbox for messages - type IB" +
//                    "\n - Exit - type Exit");
//            String option = userInput.next();  // Read user input
//            switch (option) {
//                case "BE":
//                    if (ec.get_events().size() == 0) {
//                        System.out.println("===== Entities.Event Browser =====" +
//                                "\nNo events have been scheduled. Cannot perform this action.");
//                    } else {
//                        System.out.println("===== Entities.Event Browser =====");
//                        System.out.println("Which date would you like to see events for?");
//                        String date = userInput.next();
//                        LocalDate dateF = eventManager.date_formatting_date(date);
////                        System.out.println("What start time would you like to see events for?");
////                        String start = userInput.next();
////                        LocalTime startF = eventManager.date_formatting_time(start);
////                        System.out.println("What end time would you like to see events for?");
////                        String end = userInput.next();
////                      System.out.println("And end time");
////                        LocalTime endF = eventManager.date_formatting_time(end);
//                        ArrayList<Entities.Event> browsed = signUpSystem.browseEvents(ec.em, dateF);
//                        for (Entities.Event event : browsed) {
//                            System.out.println(eventManager.eventToString(event));
//                        }
//                    }
//                    break;
//
//                case "SU":
//                    if (ec.get_events().size() == 0) {
//                        System.out.println("===== Entities.Event Browser =====" +
//                                "\nNo events have been scheduled. Cannot perform this action.");
//                    } else {
//                        System.out.println("===== Entities.Event Sign Up =====");
//
//                        for (Entities.Event scheduled : ec.get_events()) {
//                            System.out.println(eventManager.eventToString(scheduled));
//                        }
//                        System.out.println("Enter the event id of the event you want to join.");
//                        int event_id = userInput.nextInt();
//                        //Issue with getEvents() in EM, needs to be fixed
//                        while (event_id > ec.em.getEvents().size() || event_id < 1){
//                            System.out.println("Invalid id! Please try again.");
//                            event_id = userInput.nextInt();
//                        }
//                        Entities.Event event = ec.em.find_event(event_id);
//                        if(event.getEventRoom() == null){
//                            System.out.println("Sorry, the event hasn't been assigned a room. Unable to join.\n");
//                            break;
//                        } else {
//                            boolean flag = signUpSystem.signUpEvent(attendee, event);
//                            if (flag){
//                                System.out.println("You've been registered!");
//                            } else {
//                                System.out.println("This event no longer has any space.");
//                            }
//                        }
//                    }
//                    break;
//                case "VC":
//                    if (attendee.getEventsAttending().size() == 0) {
//                        System.out.println("You are not signed up for any events.");
//                    } else {
//                        System.out.println("Here is a list of events you are signed up for:");
//                        for (Entities.Event attending: attendee.getEventsAttending()) {
//                            System.out.println("Entities.Event Name: " + attending.getName() + " Entities.Event ID: " + attending.getEventId());
//                        }
//                        System.out.println("Would you like to cancel attendance at an event? Type Yes or No.");
//                        String toCancel = userInput.next();
//                        boolean cancelled = false;
//                        if (toCancel.equals("Yes")) {
//                            System.out.println("What event would you like to cancel attendance for? Type the ID.");
//                            String eventID = userInput.next();
//                            ArrayList<Entities.Event> attending_copy = new ArrayList<Entities.Event>(attendee.getEventsAttending());
//                            for (Entities.Event attending: attending_copy){
//                                if (attending.getEventId() == Integer.parseInt(eventID)) {
//                                    um.cancelRegistration(attendee, attending);
//                                    cancelled = true;
//                                } else {
//                                    cancelled = false;
//                                }
//                            }
//                            if (cancelled) {
//                                System.out.println("Attendance cancelled for this event.");
//                            } else {
//                                System.out.println("You were never signed up for this event.");
//                            }
//                        }
//                    }
//                    break;
//                case "IB":
//                    // Five options for Entities.Attendee
//                    System.out.println("============== Inbox ==================" +
//                            "\n - Send a message - SM" +
//                            "\n - Add a contact - type AD" +
//                            "\n - View messaging history - type MH" +
//                            "\n - View contacts - type CO" +
//                            "\n - Exit - type Exit"); // ask about Exit
//                    String options = userInput.next();
//                    switch (options) {
//                        // Send a message - SM
//                        case "SM":
//                            if (attendee.contacts.size() == 0) {
//                                System.out.println("You do not have any contacts to message. You must add them first.");
//                            } else {
//                                System.out.println("Enter the email of the person you would like to message.");
//                                String receiver_email = userInput.next();
//                                System.out.println("Enter the message you would like to send.");
//                                String message = userInput.next();
//                                // Check to see if the person they want to message exists in the system
//                                if (um.checkUserExists(receiver_email)) {
//                                    // Check to see if they are allowed to message the person based on the type of Entities.User,
//                                    // by using the message method from UseCase.UserManager
//                                    // If the person they want to message is not in their contacts, they are added via
//                                    // the attendeeMessage method inside the message method in UseCase.UserManager
//                                    // The attendeeMessage method also handles appending the sent message to the
//                                    // appropriate lists of the sender and receiver
//                                    boolean flag = ms.sendAttendeeMessage(attendee, um.findUser(receiver_email), message);
//                                    if (flag){
//                                        System.out.println("Message sent!");
//                                    } else {
//                                        System.out.println("Error. You do not have permission to message this person.");
//                                    }
//                                } else { // Executed when the person that the Entities.Attendee wants to message does not exist
//                                    System.out.println("Error. This person does not exist in our records.");
//                                }
//                            }
//                            break;
//                        // Add a contact - AD
//                        case "AD":
//                            System.out.println("Enter the email of the person you would like to add as a contact.");
//                            String newContact = userInput.next();
//                            // Check to see if the person exists
//                            if (um.checkUserExists(newContact)) {
//                                // Checks to see that they can add this type of person and that person they want to add
//                                // is not already in their contacts list
//                                boolean is_Attendee = (um.findUser(newContact).userType() == 'A');
//                                boolean is_Speaker = (um.findUser(newContact).userType() == 'S');
//                                if ((is_Attendee || is_Speaker) && !attendee.contacts.contains(um.findUser(newContact))) {
//                                    attendee.addContact(um.findUser(newContact)); //adds the contact to their list
//                                    System.out.println("Added contact successfully.");
//                                } else {
//                                    System.out.println("Error. You do not have permission to add this person or " +
//                                            "this person is already in your contacts.");
//                                }
//                            } else { // Executed when the person that the Entities.Attendee wants to add does not exist
//                                System.out.println("Error. This person does not exist in our records.");
//                            }
//                            break;
//                        // View messaging history - MH
//                        case "MH":
//                            System.out.println("Enter the email of the contact to view your message history with them.");
//                            String contact = userInput.next();
//                            // Check conditions similar to above two methods and print appropriate messages if errors
//                            if (um.checkUserExists(contact) && attendee.getContacts().contains(um.findUser(contact))) {
//                                ArrayList<String> messages_received = attendee.getMessagesReceived().get(um.findUser(contact));
//                                System.out.println("Enter the number of messages you would like to see.");
//                                String num = userInput.next();
//                                if (!(messages_received.size() == 0)) {
//                                    while (Integer.parseInt(num) > messages_received.size()) {
//                                        System.out.println("Enter the number of messages you would like to see.");
//                                        num = userInput.next();
//                                    }
//                                    for (int i = messages_received.size() - Integer.parseInt(num); i < messages_received.size(); i++) {
//                                        System.out.println(messages_received.get(i));
//                                    }
//                                } else {
//                                    System.out.println("You have no messages from this person.");
//                                }
//                            } else { // Executed when the person that the Entities.Attendee wants to add does not exist
//                                System.out.println("Error. This person does not exist in our records or they are not in your contact list.");
//                            }
//                            break;
//                        // View contacts - CO
//                        case "CO":
//                            if (!(attendee.contacts.size() == 0)) {
//                                System.out.println("============== Your Contacts ==================");
//                                // Loop through their contacts list and print out the names of each of their contacts
//                                for (Entities.User c: attendee.contacts) {
//                                    System.out.println(c.name);
//                                }
//                            } else {
//                                System.out.println("You do not have any contacts.");
//                            }
//                            break;
//                    }
//                    break;
//
//                case "Exit":
//                    on_page = false;
//
//            }
//        }
//    }
//
//    public static void SpeakerInterface(Controllers.SignUpSystem signUpSystem, UseCase.EventManager eventManager, TalkManager tm, EventController ec,
//                                        UseCase.UserManager um, Controllers.MessagingSystem ms, String email) {
//        UseCase.UserManager userManager = new UseCase.UserManager();
//
//        Entities.Speaker speaker = (Entities.Speaker) um.findUser(email);
//        boolean on_page = true;
//
//        while (on_page) {
//            Scanner userInput = new Scanner(System.in);  // Scanner
//            System.out.println("==============Entities.Speaker Interface==================" +
//                    "\n -Browse My Talks- type BMT" +
//                    "\n -Inbox- type IB" +
//                    "\n -Exit- type Exit");
//            String option = userInput.next();
//            switch (option) {
//                case "BMT":
//                    System.out.println("These are the talks that you are speaking at:");
//                    if (speaker.getTalksSpeaking().size() == 0) {
//                        System.out.println("You are currently not scheduled to speak at any talks.");
//                    } else {
//                        for (int i = 0; i < speaker.getTalksSpeaking().size(); i++) {
//                            System.out.println(speaker.getTalksSpeaking().get(i));
//                        }
//                    }
//                    break;
//                case "IB":
//                    System.out.println("============== Inbox ==================" +
//                            "\n - Send a Message to Attendees - enter SM" +
//                            "\n - View Messaging History and Respond to Individual Entities.Attendee- enter VMH" +
//                            "\n - Exit - enter E");
//                    String inboxOption = userInput.next();
//                    switch (inboxOption) {
//                        case "SM":
//                            System.out.println("You are currently scheduled to talk at " + speaker.getTalksSpeaking().size() + " talks.");
//                            System.out.println("List the total number of talks that you want to message attendees for.");
//                            int numberOfTalks = userInput.nextInt();
//
//                            while (numberOfTalks > speaker.getTalksSpeaking().size()) { //Checks if valid number of talks was entered
//                                System.out.println("You are not speaking at this many talks. Please re-enter.");
//                                numberOfTalks = userInput.nextInt();
//                            }
//
//                            if (numberOfTalks > 0) {
//                                ArrayList<Integer> speakersEvents = new ArrayList<>();
//                                for (Entities.Talk speakerTalk: speaker.getTalksSpeaking()) {
//                                    speakersEvents.add(speakerTalk.getEvent().getEventId());
//                                    tm.toString(speakerTalk);
//                                }
//                                // Allows speaker to enter all the talks that they want to send to a message to the attendees of.
//                                System.out.println("Please enter ids of all the events that you " +
//                                        "want to send messages to the attendees of. Use this format: 1,2,3");
//                                String eventsInput = userInput.next();
//                                String[] eventsToMessage = eventsInput.split(",");
//
//                                for (String eventID: eventsToMessage) {
//                                    if (speakersEvents.contains(Integer.parseInt(eventID))) {
//                                        System.out.println("Please enter the message you would like to send to all the attendees in the event with ID: " +
//                                                eventID);
//                                        String messageToSend = userInput.next();
//                                        ms.sendMessageSpeaker(speaker, ec.em.find_event(Integer.parseInt(eventID)), messageToSend);
//                                    } else {
//                                        System.out.println("Error. You cannot message attendees for this event.");
//                                    }
//                                }
//                            } else {
//                                System.out.println("No attendees will be messaged");
//                                on_page = false; // exit SM
//                            }
//                            break;
//                        case "VMH":
//                            boolean hasMessagesFromContact = false;
//                            System.out.println("Enter an email to view your message history with them.");
//                            String emailOfContact = userInput.next();
//
//                            // Check if this user exists in the system
//                            if (userManager.checkUserExists(email)) {
//                                if (speaker.getMessagesReceived().get(um.findUser(emailOfContact)) == null){
//                                    System.out.println("No history with them");
//                                }
//                                else{
//                                    System.out.println("Enter the number of messages you would like to see.");
//                                    String numMessages = userInput.next();
//                                    ArrayList<String> messages_received = speaker.getMessagesReceived().get(um.findUser(emailOfContact));
////                                    System.out.println(messages_received.size());
//                                    if (!(messages_received.size() == 0)) {
//                                        // Code runs when there are more than 0 messages between speaker and the attendee
//                                        while (Integer.parseInt(numMessages) > messages_received.size()) {
//                                            System.out.println("Enter the number of messages you would like to see.");
//                                            numMessages = userInput.next();
//                                        }
//                                        for (int i = messages_received.size() - Integer.parseInt(numMessages); i < messages_received.size(); i++) {
//                                            System.out.println(messages_received.get(i));
//                                            hasMessagesFromContact = true;
//                                        }
//                                    } else {
//                                        // Code runs when there are zero messages between the two
//                                        System.out.println("You have no messages from this person.");
//                                    }
//                                }
//                            } else {
//                                // runs if the user does not exist
//                                System.out.println("Error. This person does not exist in our records.");
//                            }
//
//                            // Interface to respond to a message after having checked the thread of prior messages
//                            if (hasMessagesFromContact) {
//                                System.out.println("Would you like to respond to this Entities.Attendee? Yes or No.");
//                                String respondDecision = userInput.next();
//                                switch (respondDecision) {
//                                    case "Yes":
//                                        System.out.println("Please enter your response.");
//                                        String messageResponse = userInput.next();
//                                        ms.sendAttendeeMessage(speaker, um.findUser(email), messageResponse);
//                                        break;
//                                    case "No":
//                                        on_page = false;
//
//                                }
//                            }
//                            break;
//
//                        case "E":
//                            on_page = false;
//                   }
//                case "Exit":
//                        on_page = false;
//            }
//        }
//    }
//
//    public static void LoginInterface() throws ClassNotFoundException, IOException {
//
//        UseCase.UserManager userManager = new UseCase.UserManager();
//        LoginController loginSystem = new LoginController();
//        EventController ec = new EventController();
//        Controllers.SignUpSystem su = new Controllers.SignUpSystem();
//        Controllers.MessagingSystem ms = new Controllers.MessagingSystem();
//        Scanner userInput = new Scanner(System.in);
//        UseCase.EventManager em = new UseCase.EventManager();
//        TalkManager tm = new TalkManager();
//        boolean signed_in = false;
//
//        while (!signed_in) {
//            System.out.println("|===== Phase 1 login =====|");
//            System.out.println("Enter your email");
//            String email = userInput.next();
//            System.out.println("Enter your password");
//            String password = userInput.next();
//            signed_in = loginSystem.checkLogIn(email, password);
//            if (!signed_in) {
//                System.out.println("Invalid login credentials");
//            }
//
//            if (signed_in) {
//                System.out.println("Logged In....");
//                boolean homeScreen = true;
//                while (homeScreen) {
//                    System.out.println("=============== Phase 1 System ===============");
//                    char usertype = userManager.findUser(email).userType();
//                    if (usertype == 'O') {
//                        System.out.println("-Organiser Options- OO");
//                    } else if (usertype == 'A') {
//                        System.out.println("-Entities.Attendee Options - AO");
//                    } else if (usertype == 'S') {
//                        System.out.println("-Entities.Speaker Options - SO");
//                    }
//                    System.out.println("-Log Out- LO");
//                    String option = userInput.next();
//
//                    switch (option) {
//                        case "AO":
//                            AttendeeInterface(su,em,ec, userManager, ms, email);
//                            break;
//
//                        case "OO":
//                            OrganizerInterface(ec, userManager, ms, email);
//                            break;
//
//                        case "SO":
//                            SpeakerInterface(su,em,tm, ec, userManager, ms, email);
//                            break;
//
//                        case "LO":
//                            signed_in = true;
//                            homeScreen = false;
//                    }
//                }
//                signed_in = false;
//            }
//        }
//    }
//
//
//
//    public static class EventPlannerSystem {
//        public void run() throws IOException, ClassNotFoundException {
//            LoginInterface();
//        }
//
//    }
//
//    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        EventPlannerSystem e = new EventPlannerSystem();
//        e.run();
//
//    }
//}
//
//
//
