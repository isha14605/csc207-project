import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TalkManager implements Serializable{
    public ArrayList<Talk> talks = new ArrayList<Talk>();

    public TalkManager(){}

    /** code use case classes that directly add/ create new entities **/
    protected Talk create_talk(LocalTime startTime, LocalTime endTime, Event event){
        Talk talk = new Talk(startTime, endTime, event);
        talk.setId(talks.size()+1);
        talks.add(talk);
        return talk;
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

    public void writeToFile(String fileName) throws IOException {
        OutputStream file = new FileOutputStream(fileName);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        output.writeObject(talks);
        output.close();

    }

    public ArrayList<Talk> readFile(String fileName) throws IOException, ClassNotFoundException {
        try {
            InputStream file = new FileInputStream(fileName);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            ArrayList<Talk> talks1 = (ArrayList<Talk>) input.readObject();
            input.close();
            return talks1;
        } catch (IOException | ClassNotFoundException ignored) {
            System.out.println("couldn't read room file.");
        }
        return talks;
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
