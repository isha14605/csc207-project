import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

class UserInterface {

    public static void OrganizerInterface(EventController eventController) throws IOException {

        boolean on_page = true;
        while (on_page) {
            EventManager em = eventController.em;
            RoomManager rm = eventController.rm;
            Scanner userInput = new Scanner(System.in);  // Create a Scanner object
            System.out.println("==============Organizer Interface==================" +
                    "\n -Add Event- enter AE-" +
                    "\n -Create Room- enter CR-" +
                    "\n -Event Options- enter EO-" +
                    "\n -View Events- enter VE-" +
                    "\n -View Rooms- enter VR-" +
                    "\n -Exit-");

            String option = userInput.nextLine();  // Read user input
            switch (option) {

                case "AE":
                    System.out.println("====Event Creator====");
                    System.out.println("What is the name of Event");
                    String name = userInput.nextLine();
                    System.out.println("What is the name of description");
                    String description = userInput.nextLine();
                    System.out.println("When does the event start");
                    String start = userInput.nextLine();
                    System.out.println("When does the event end");
                    String end = userInput.nextLine();
                    System.out.println("What date is the event");
                    String date = userInput.nextLine();

                    eventController.add_event(name, description, start, end, date);
                    break;

                case "CR":
                    System.out.println("====Room Creator====");

                    System.out.println("What is the name of the room");
                    String room_name = userInput.nextLine();
                    System.out.println("How many people can the room hold");
                    int capacity = userInput.nextInt();
                    System.out.println("When does the Room Open");
                    String open = userInput.next();
                    System.out.println("When does the Room Close");
                    String close = userInput.next();

                    eventController.add_room(room_name, capacity, open, close);

                    break;

                case "VE":

                    em.print_events();

                    if (eventController.get_events().size() == 0) {
                        System.out.println("No events are Scheduled");
                    }
                    break;
                case "VR":
                    RoomManager roomManager = new RoomManager();
                    for (Room room : eventController.get_rooms()) {
                        System.out.println(roomManager.roomToString(room));
                    }
                    if (eventController.get_rooms().size() == 0) {
                        System.out.println("No rooms are made");
                    }
                    break;

                case "EO":
                    System.out.println("-Event Options-");
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
                                System.out.println("need implementation");
                                break;

                            case "SR":
                                if (eventController.get_rooms().size() == 0) {
                                    System.out.println("No rooms to preform actions to! \n");
                                } else {
                                    System.out.println("What event do you want to Schedule room for");

                                    em.print_events();

                                    event_id = userInput.nextInt();
                                    if(em.event_exist(event_id)){
                                        System.out.println("What room what do you want ot schedule");
                                        for (Room room : eventController.get_rooms()) {
                                            System.out.println(rm.roomToString(room));
                                        }
                                        room_name = userInput.next();

                                        eventController.schedule_room(room_name, event_id);
                                    }else{
                                        System.out.println("Event does not exist.");
                                    }
                                }
                                break;

                            case "AT":
                                System.out.println("What event you adding talk to?");
                                em.print_events();
                                event_id = userInput.nextInt();
                                System.out.println("When does the talk start");
                                start = userInput.next();
                                System.out.println("When does the talk end");
                                end = userInput.next();
                                if(eventController.add_talk(start,end,event_id)){
                                    System.out.println("Talk was added to Event " + event_id);
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
                                         UserManager um) {
        Attendee attendee = new Attendee("test", "test", "test");

        boolean on_page = true;
        while (on_page) {
            Scanner userInput = new Scanner(System.in);  // Create a Scanner object
            System.out.println("==============Attendee Interface==================" +
                    "\n - Browse events - type BE" +
                    "\n - Signup for events - type SU" +
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
                        System.out.println("What start time would you like to see events for?");
                        String start = userInput.next();
                        LocalTime startF = eventManager.date_formatting_time(start);
                        System.out.println("What end time would you like to see events for?");
                        String end = userInput.next();
//                      System.out.println("And end time");
                        LocalTime endF = eventManager.date_formatting_time(end);
                        ArrayList<Event> browsed = signUpSystem.browseEvents(dateF, startF, endF);
                        for (Event event : browsed) {
                            eventManager.eventToString(event);
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
                            eventManager.eventToString(scheduled);
                        }
                        System.out.println("Enter the event id of the event you want to join.");
                        int event_id = userInput.nextInt();
                        Event event = ec.em.find_event(event_id);
                        if(event.getEventRoom() == null){
                            System.out.println("Sorry, the event hasn't been assigned a room. Unable to join.\n");
                            break;
                        }
                        signUpSystem.signUp(attendee, event);
                    }

                case "IB":
                    // Four options for Attendee
                    System.out.println("============== Inbox ==================" +
                            "\n - Send a message - SM" +
                            "\n - Add a contact - type AD" +
                            "\n - View messaging history - type MH" +
                            "\n - View contacts - type CO");
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
                                    boolean flag = um.message(attendee, um.findUser(receiver_email), message);
                                    if (flag){
                                        System.out.println("Message sent!");
                                    } else {
                                        System.out.println("Error. You do not have permission to message this person.");
                                    }
                                } else { // Executed when the person that the Attendee wants to message does not exist
                                    System.out.println("Error. This person does not exist in our records.");
                                }
                            }
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
                                            "this person is already in your contacts");
                                }
                            } else { // Executed when the person that the Attendee wants to add does not exist
                                System.out.println("Error. This person does not exist in our records.");
                            }
                        // View messaging history - MH
                        case "MH":
                            // "Enter the name of a contact to view your message history with them."
                            // Check conditions similar to above two methods and print appropriate messages if errors
                            // "Enter the timeframe between which you would like to view messages with this person"
                            // Check if messages are sent between the two people during this timeframe
                            // If yes, print "Message history with <the person's name>" and print as you loop
                            // through messages sent (<name>:) and received (<name>:) in an alternating format
                            // If no, print "No messages were exchanged between this timeframe with this person."
                    }



                    // View contacts - CO
                        // Loop through their contacts list and print out the names of each of their contacts
                    break;

                case "Exit":
                    on_page = false;

            }
        }
    }

    public static void SpeakerInterface() {
        UserManager userManager = new UserManager();
        SignUpSystem signUpSystem = new SignUpSystem();
        EventManager eventManager = new EventManager();

        //s = userManager.findUser(email);
        Speaker s = new Speaker("test", "test", "test");
        boolean on_page = true;

        while (on_page) {
            Scanner userInput = new Scanner(System.in);  // Create a Scanner object
            System.out.println("==============Speaker Interface==================" +
                    "\n -Browse My Talks- enter BMT" +
                    "\n -Inbox- IB" +
                    "\n -Exit-");
            String option = userInput.next();  // Read user input

            switch (option) {
                case "BMT":
                    System.out.println("These are the talks that you are speaking at:");
                    if (s.getTalksSpeaking().size() == 0) {
                        System.out.println("You are not scheduled to speak at any talks");
                    } else {
                        for (int i = 0; i < s.getTalksSpeaking().size(); i++) {
                            System.out.println(s.getTalksSpeaking().get(i));
                        }
                    }
                    break;
                case "IB":
                    System.out.println("Not Implemented Yet");
                    break;
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
                System.out.println("invalid login credentials");
            }

            if (signed_in) {
                System.out.println("Logged In....");
                boolean homeScreen = true;
                while (homeScreen) {
                    System.out.println("=============== Phase 1 System ===============");
                    char usertype = userManager.findUser(email).userType();
                    System.out.println("-User Options- UO");
                    if (usertype == 'O') {
                        System.out.println("-Organiser Options- OO");
                    }
                    System.out.println("-Log Out- LO");
                    String option = userInput.next();

                    switch (option) {
                        case "UO":
                            AttendeeInterface(su,em,ec, userManager);
                            break;

                        case "OO":
                            OrganizerInterface(ec);
                            break;

                        case "LO":
                            signed_in = true;
                            homeScreen = false;
                    }
                }
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



