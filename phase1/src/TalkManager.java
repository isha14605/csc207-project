import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class TalkManager {
    private final ArrayList<Talk> talks = new ArrayList<Talk>();

    public TalkManager(){}

    /** code use case classes that directly add/ create new entities **/
    protected Talk create_talk(LocalDateTime startTime, LocalDateTime endTime, Event event){
        try {
            Talk talk = new Talk(startTime, endTime, event);
            talks.add(talk);
            return talk;
        }
        catch(Exception e){
            System.out.println("invalid input");
        }

        return null;
    }
    protected void add_talk(Talk talk, Event event) {
        for (Talk scheduled : event.getTalks()) {
            if (talk.getStartTime().equals(scheduled.getStartTime())) {
                return;
            }
        }
        event.addTalk(talk);
    }

    protected void schedule_speaker(Speaker speaker, Talk talk){
        speaker.addTalk(talk);
        talk.setSpeaker(speaker);
    }

    /** Checkers **/
    protected boolean speaker_can_be_scheduled(Talk talk){
        return talk.getSpeaker() == null;
    }

    /** getter **/
    protected ArrayList<Talk> get_talks_at(LocalDateTime time, Event event){
        ArrayList<Talk> same_time = new ArrayList<>();
        for(Talk talk: event.getTalks()){
            if(talk.getStartTime().equals(time)){
                same_time.add(talk);
            }
        }
        return same_time;
    }
    private ArrayList<Talk> getTalks(){
        return talks;
    }

    /** helper functions **/
    protected LocalDateTime get_localDateTime(LocalDate date, LocalTime time){
        return LocalDateTime.of(date, time);
    }
    protected LocalDateTime date_formatting_DT(String date){
        int len = date.length();
        if(len == 16){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return LocalDateTime.parse(date, formatter);
        }
        throw new IllegalArgumentException("Input is not a valid format");
    }
    protected LocalDate date_formatting_date(String date){
        int len = date.length();
        if(len == 10){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(date, formatter);
        }
        throw new IllegalArgumentException("Input is not a valid format");
    }

    protected LocalTime date_formatting_time(String date){
        int len = date.length();
        if(len == 5){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return LocalTime.parse(date, formatter);
        }
        throw new IllegalArgumentException("Input is not a valid format");
    }

    public Event findTalk(String talk){
        for(Talk t: talks){
            if (t.getEvent().equals(talk)){
                return t.getEvent();

            }
        }
        return null;

    }


}
