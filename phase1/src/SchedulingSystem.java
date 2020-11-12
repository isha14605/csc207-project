public class SchedulingSystem {
    EventManager em = new EventManager();

    public void add_event(String name, String description, String start, String end){
        em.add_Event(name, description,
                em.date_formatting_DT(start), em.date_formatting_DT(end));
    }

    public void add_room(String name, Integer capacity, String start, String end, Integer event_id){
        em.add_room(em.create_room(name, capacity,
                em.date_formatting_time(start), em.date_formatting_time(end)),
                em.find_event(event_id));
    }

    public void add_talk(String start, String end, Integer event_id){
        Talk talk = em.create_talk(em.date_formatting_DT(start),
                em.date_formatting_DT(end), em.find_event(event_id));
        if(em.time_conflict(talk,em.find_event(event_id))){
            em.add_talk(talk, em.find_event(event_id));
        }

    }

    public void schedule_room(Room room, Event event){

    }

    public void schedule_speaker(Speaker speaker, Talk talk, Event event){
        em.schedule_speaker(speaker, talk, event);
    }
}
