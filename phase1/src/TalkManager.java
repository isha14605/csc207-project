import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Manages Talks and Functionality
 *
 * @author Chevoy Ingram
 * @version 1.0
 */

public class TalkManager implements Serializable{
    private final ArrayList<Talk> talks = new ArrayList<Talk>();

    public TalkManager(){}

    /** Allows user to create a talk entity
     * @param startTime start time of the talk
     * @param endTime end time of the talk
     * @param event the event that talk will he held in
     * @return The talk entity **/
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

    /**Schedules speaker to event
     *@param talk talk that speaker is being scheduled to.
     *@param speaker Speaker that is being scheduled
     * */
    protected void schedule_speaker(Speaker speaker, Talk talk){
        speaker.addTalk(talk);
        talk.setSpeaker(speaker);
    }

    /** Checkers **/

    /**Check if speaker can be scheduled for talk
     *@param talk talk being checked
     *@return true if speaker can be scheduled */
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

    protected ArrayList<Talk> getTalks(){
        return talks;
    }

    /** Prints the string version of this talk
     * @param talk talk converted
     * */
    public void toString(Talk talk){
        String speakerN = null;
        Speaker speaker = talk.getSpeaker();

        if(speaker == null){
            speakerN = "None";
        }else {
            speakerN = speaker.getName();
        }
        System.out.println("Talk id: " + talk.getId() +" Speaker : " +
                speakerN + " at " + talk.getStartTime()
                + " to " + talk.getEndTime());
    }

    /** Prints the string version of all talks within a event
     * @param event what talks are being converted
     * */
    protected void print_talks(Event event){
        for (Talk talk: event.getTalks()) {
            toString(talk);
        }
    }

    protected Talk getTalk(int talkId){
        for(Talk talk: talks){
            if(talk.getId() == talkId){
                return talk;
            }
        }
        System.out.println("talk not found");
        return null;
    }
    /** helper functions **/

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

        output.writeObject(this);
        output.close();

    }

    public TalkManager readFile(String fileName) throws IOException, ClassNotFoundException {
        try {
            InputStream file = new FileInputStream(fileName);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            TalkManager tm = (TalkManager) input.readObject();
            input.close();
            return tm;
        } catch (IOException | ClassNotFoundException ignored) {
            System.out.println("couldn't read room file.");
        }
        return new TalkManager();
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
