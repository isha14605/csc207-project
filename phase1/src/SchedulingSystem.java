import java.util.Scanner;


class Interface {
    public static void main(String[] args) {
        EventController eventController = new EventController();
        boolean running = true;
        while (running){
            Scanner userInput = new Scanner(System.in);  // Create a Scanner object
            System.out.println("==============Organizer Interface==================");
            System.out.println("Hello organizer what would you like to do");
            System.out.println("-Add Event- enter AE");
            System.out.println("-Create Room- enter CR");
            System.out.println("-Event Options- enter EO");
            System.out.println("-View Events- enter VE");
            System.out.println("-View Rooms- enter VR");
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
                    EventManager eventManager = new EventManager();
                    for(Event event: eventController.get_events()){
                        System.out.println(eventManager.eventToString(event));
                    }
                    if(eventController.get_events().size() == 0){
                        System.out.println("No events are Scheduled");
                    }
                    break;
                case "VR":
                    RoomManager roomManager= new RoomManager();
                    for(Room room: eventController.get_rooms()){
                        System.out.println(roomManager.roomToString(room));
                    }
                    if(eventController.get_rooms().size() == 0){
                        System.out.println("No rooms are made");
                    }
                    break;

                case "EO":
                    System.out.println("-Event Options-");
                    if(eventController.get_events().size() == 0){
                        System.out.println("no events schedule to do actions! \n");
                    }
                    else {
                        System.out.println("-Schedule Speaker- SS");
                        System.out.println("-Schedule Room- SR");
                        System.out.println("-Create Talk- enter CT");
                        String event_options = userInput.nextLine();
                        switch (event_options) {
                            case "CT":
                                eventManager = new EventManager();
                                System.out.println("====Talk Creator====");
                                System.out.println("What Event Would you like to add talk to");
                                for(Event event: eventController.get_events()){
                                    System.out.println(eventManager.eventToString(event));
                                }
                                int event_id = userInput.nextInt();
                                if(eventManager.find_event(event_id).getEventRoom() == null){
                                    System.out.println("Event needs to be scheduled a room before" +
                                            "talks can be added");
                                }
                                else{
                                    System.out.println("When does the talk start");
                                    start = userInput.next();
                                    System.out.println("When does the talk end");
                                    end = userInput.next();
                                    eventController.add_talk(start,end,event_id);

                                }
                                break;

                            case "SS":
                                System.out.println("need implementation");
                                break;

                            case "SR":
                                if(eventController.get_rooms().size() == 0){
                                    System.out.println("No rooms to preform actions to! \n" );
                                }
                                else{
                                    System.out.println("What event do you want to Schedule room for");

                                    eventManager = new EventManager();

                                    for(Event event: eventController.get_events()){
                                        System.out.println(eventManager.eventToString(event));
                                    }
                                    event_id = userInput.nextInt();

                                    System.out.println("What room what do you want ot schedule");
                                    roomManager= new RoomManager();
                                    for(Room room: eventController.get_rooms()){
                                        System.out.println(roomManager.roomToString(room));
                                    }
                                    room_name = userInput.next();

                                    eventController.schedule_room(room_name,event_id);


                                }
                                break;
                        }
                    }
                    break;


                case "exit":
                    running = false;

            }
        }
    }
}
