public class SchedulingSystem {
    EventManager em = new EventManager();

    public void add_event(String name, String description, String start, String end){
        em.addEvent(name, description,
                em.dateFormattingDT(start), em.dateFormattingDT(end));
    }

    public void add_room(String name, Integer capacity, String start, String end, Integer event_id){
        em.addRoom(em.createRoom(name, capacity,
                em.dateFormattingTime(start), em.dateFormattingTime(end)),
                em.findEvent(event_id));
    }

    public void add_talk(String start, String end, Integer event_id){
        Talk talk = em.createTalk(em.dateFormattingDT(start),
                em.dateFormattingDT(end), em.findEvent(event_id));
        if(!em.timeConflict(talk,em.findEvent(event_id)) && em.isRoomOpen(talk,
                em.findEvent(event_id).getEvent_room())){

            em.addTalk(talk, em.findEvent(event_id));
        }

    }

    public void schedule_room(String room_name, Integer event_id){
        Event event = em.findEvent(event_id);
        Room room = em.findRoom(room_name);
        if(!em.isRoomBooked(room, event) && event.getEvent_room() != null){
            em.scheduleRoom(room, event);
            room.add_bookings(event, em.getLocalDateTime(event.getEvent_date(),event.getStart_time()),
                    em.getLocalDateTime(event.getEvent_date(),event.getEnd_time()));
        }
    }

    public void schedule_speaker(Speaker speaker, Talk talk, Event event){
        em.scheduleSpeaker(speaker, talk, event);
    }
}
