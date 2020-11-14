public class SchedulingSystem {
    EventManager em = new EventManager();

    public void addEvent(String name, String description, String start, String end){
        em.addEvent(name, description,
                em.dateFormattingDT(start), em.dateFormattingDT(end));
    }

    public void addRoom(String name, Integer capacity, String start, String end, Integer eventId){
        em.addRoom(em.createRoom(name, capacity,
                em.dateFormattingTime(start), em.dateFormattingTime(end)),
                em.findEvent(eventId));
    }

    public void addTalk(String start, String end, Integer eventId){
        Talk talk = em.createTalk(em.dateFormattingDT(start),
                em.dateFormattingDT(end), em.findEvent(eventId));
        if(!em.timeConflict(talk,em.findEvent(eventId)) && em.isRoomOpen(talk,
                em.findEvent(eventId).getEventRoom())){

            em.addTalk(talk, em.findEvent(eventId));
        }

    }

    public void scheduleRoom(String room_name, Integer eventId){
        Event event = em.findEvent(eventId);
        Room room = em.findRoom(room_name);
        if(!em.isRoomBooked(room, event) && event.getEventRoom() != null){
            em.scheduleRoom(room, event);
            room.addBookings(event, em.getLocalDateTime(event.getEventDate(),event.getStartTime()),
                    em.getLocalDateTime(event.getEventDate(),event.getEndTime()));
        }
    }

    public void scheduleSpeaker(Speaker speaker, Talk talk, Event event){
        em.scheduleSpeaker(speaker, talk, event);
    }
}
