import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

class UserInterface {

    public static void OrganizerInterface() {

        EventController eventController = new EventController();
        boolean on_page = true;
        while (on_page) {
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
                    System.out.println("What is the name of the event?");
                    String name = userInput.nextLine();
                    System.out.println("What is the description of the event?");
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

                case "VE":
                    EventManager eventManager = new EventManager();
                    for (Event event : eventController.get_events()) {
                        System.out.println(eventManager.eventToString(event));
                    }
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
                    System.out.println("-Event Options-");
                    if (eventController.get_events().size() == 0) {
                        System.out.println("No events scheduled to do such actions! \n");
                    } else {
                        System.out.println("-Schedule Speaker- SS");
                        System.out.println("-Schedule Room- SR");
                        System.out.println("-Create Talk- enter CT");
                        String event_options = userInput.nextLine();
                        switch (event_options) {
                            case "CT":
                                eventManager = new EventManager();
                                System.out.println("====Talk Creator====");
                                System.out.println("What event would you like to add this talk to?");
                                for (Event event : eventController.get_events()) {
                                    System.out.println(eventManager.eventToString(event));
                                }
                                int event_id = userInput.nextInt();
                                if (eventManager.find_event(event_id).getEventRoom() == null) {
                                    System.out.println("Event needs to be scheduled a room before" +
                                            "talks can be added.");
                                } else {
                                    System.out.println("When does the talk start?");
                                    start = userInput.next();
                                    System.out.println("When does the talk end?");
                                    end = userInput.next();
                                    eventController.add_talk(start, end, event_id);

                                }
                                break;

                            case "SS":
                                System.out.println("need implementation");
                                break;

                            case "SR":
                                if (eventController.get_rooms().size() == 0) {
                                    System.out.println("No rooms to perform actions to! \n");
                                } else {
                                    System.out.println("What event do you want to schedule room for?");

                                    eventManager = new EventManager();

                                    for (Event event : eventController.get_events()) {
                                        System.out.println(eventManager.eventToString(event));
                                    }
                                    event_id = userInput.nextInt();

                                    System.out.println("What room do you want to schedule?");
                                    roomManager = new RoomManager();
                                    for (Room room : eventController.get_rooms()) {
                                        System.out.println(roomManager.roomToString(room));
                                    }
                                    room_name = userInput.next();

                                    eventController.schedule_room(room_name, event_id);


                                }
                                break;
                        }
                    }
                    break;

                case "exit":
                    on_page = false;

            }
        }

    }

    public static void AttendeeInterface() {
        UserManager userManager = new UserManager();
        SignUpSystem signUpSystem = new SignUpSystem();
        EventManager eventManager = new EventManager();
        Attendee attendee = new Attendee("test", "test", "test");
        boolean on_page = true;
        while (on_page) {
            Scanner userInput = new Scanner(System.in);  // Create a Scanner object
            System.out.println("==============Attendant Interface==================" +
                    "\n -Browse Events- BE" +
                    "\n -Event SignUp- enter SU" +
                    "\n -Inbox- IB" +
                    "\n -Exit-");
            String option = userInput.next();  // Read user input
            switch (option) {
                case "BE":
                    if (eventManager.getEvents().size() == 0) {
                        System.out.println("===== Event Browser =====" +
                                "\nNo events have been scheduled. Cannot perform actions.");
                    } else {
                        System.out.println("===== Event Browser =====");
                        System.out.println("What date would you like to see events from?");
                        String date = userInput.next();
                        LocalDate dateF = eventManager.date_formatting_date(date);
                        System.out.println("After what start time would you like to see events for?");
                        String start = userInput.next();
                        LocalTime startF = eventManager.date_formatting_time(start);
                        System.out.println("Before what end time would you like to see events for?");
                        String end = userInput.next();
                        System.out.println("And end time");
                        LocalTime endF = eventManager.date_formatting_time(end);
                        ArrayList<Event> browsed = signUpSystem.browseEvents(dateF, startF, endF);
                        for (Event event : browsed) {
                            eventManager.eventToString(event);
                        }
                    }
                    break;

                case "SU":
                    if (eventManager.getEvents().size() == 0) {
                        System.out.println("===== Event Browser =====" +
                                "\nNo events have been scheduled. Cannot perform actions.");
                    } else {
                        System.out.println("===== Event Sign Up =====");
                        ArrayList<Event> events = eventManager.getEvents();
                        for (Event scheduled : events) {
                            eventManager.eventToString(scheduled);
                        }
                        System.out.println("Enter the event id of the event you want to join.");
                        int event_id = userInput.nextInt();
                        Event event = eventManager.find_event(event_id);
                        signUpSystem.signUp(attendee, event);
                    }
                case "IB":
                    System.out.println("not implemented yet");
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
                        System.out.println("You are not scheduled to speak at any talks.");
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

        public static void main (String[] args) throws FileNotFoundException {

            UserManager userManager = new UserManager();
            LoginSystem loginSystem = new LoginSystem(userManager);
            Scanner userInput = new Scanner(System.in);
            boolean signed_in = false;

            while (!signed_in) {
                System.out.println("|===== Phase 1 login =====|");
                System.out.println("Enter your email.");
                String email = userInput.next();
                System.out.println("Enter your password.");
                String password = userInput.next();
                signed_in = loginSystem.checkLogIn(email, password);
                if (!signed_in) {
                    System.out.println("Invalid login credentials.");
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
                                AttendeeInterface();
                                break;

                            case "OO":
                                OrganizerInterface();
                                break;

                            case "LO":
                                signed_in = false;
                                homeScreen = false;
                        }
                    }
                }
            }
        }
    }
