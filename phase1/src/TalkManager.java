import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Manages Talks and Functionality
 * @author Chevoy Ingram
 * @version 1.0
 */

public class TalkManager implements Serializable{
    public ArrayList<Talk> talks = new ArrayList<Talk>();

    public TalkManager(){}

    /**
     * Allows user to create a talk entity
     * @param startTime start time of the talk
     * @param endTime end time of the talk
     * @param event the event that talk will he held in
     * @return the talk entity that was created
     * @see Talk#setId(int)
     **/
    protected Talk create_talk(LocalTime startTime, LocalTime endTime, Event event){
        Talk talk = new Talk(startTime, endTime, event);
        talk.setId(talks.size()+1);
        talks.add(talk);
        return talk;
    }

    /**
     * Adds a given Entities.Talk to the list of scheduled talks in Event
     * @param talk the Entities.Talk that needs to be added to the Event.
     * @param event the Event to which the Entities.Talk is being added.
     * @see Talk#getStartTime()
     * @see Event#getTalks()
     */
    protected void add_talk(Talk talk, Event event) {
        for (Talk scheduled : event.getTalks()) {
            if (talk.getStartTime().equals(scheduled.getStartTime())) {
                return;
            }
        }
        event.addTalk(talk);
    }

    /**
     * Schedules speaker to event
     *@param talk talk that speaker is being scheduled to.
     *@param speaker Entities.Speaker that is being scheduled
     *@see Speaker#addTalk(Talk)
     * @see Talk#setSpeaker(Speaker)
     */
    protected void schedule_speaker(Speaker speaker, Talk talk){
        speaker.addTalk(talk);
        talk.setSpeaker(speaker);
    }

    /**
     * Check if speaker can be scheduled for talk
     *@param talk talk being checked
     *@return true if speaker can be scheduled
     *@see Talk#getSpeaker()
     */
    protected boolean speaker_can_be_scheduled(Talk talk){
        return talk.getSpeaker() == null;
    }

    /**
     * Obtains and returns a list of Talks from an Event that start at the same time.
     * @param time the time that the talks at the event should start
     * @param event the event from which the talks are being obtained based on their start time
     * @return an ArrayList of Entities.Talk objects that contain talks at the Event that start at the desired time smae_time.
     * @see Event#getTalks()
     */
    protected ArrayList<Talk> get_talks_at(LocalDateTime time, Event event){
        ArrayList<Talk> same_time = new ArrayList<>();
        for(Talk talk: event.getTalks()){
            if(talk.getStartTime().equals(time)){
                same_time.add(talk);
            }
        }
        return same_time;
    }

    /**
     * Retrieves and returns a list of Talks
     * @return a list of Talks
     * @see Talk
     */
    protected ArrayList<Talk> getTalks(){
        return talks;
    }

    /**
     * Prints a String interpretation of this Entities.Talk
     * @param talk talk converted
     * @see Talk
     * @see Speaker
     */
    public void toString(Talk talk){
        String speakerN = null;
        Speaker speaker = talk.getSpeaker();

        if(speaker == null){
            speakerN = "None";
        }else {
            speakerN = speaker.getName();
        }
        System.out.println("Entities.Talk id: " + talk.getId() +" Entities.Speaker : " +
                speakerN + " at " + talk.getStartTime()
                + " to " + talk.getEndTime() +"Event it is part of: " + talk.getEvent());
    }

    /**
     * Prints the string version of all talks within a event
     * @param event what talks are being converted
     * */
    protected void print_talks(Event event){
        for (Talk talk: event.getTalks()) {
            toString(talk);
        }
    }

    /**
     * Retrieves the Entities.Talk that is associated with the provided talkId
     * @param talkId
     * @return the Entities.Talk object associated with talkId
     */
    protected Talk getTalk(int talkId){
        for(Talk talk: talks){
            if(talk.getId() == talkId){
                return talk;
            }
        }
        System.out.println("Entities.Talk Not Found");
        return null;
    }

    /** Helper Functions **/

    /**
     *
     * @param date
     * @return
     */
    protected LocalTime date_formatting_time(String date){
        int len = date.length();
        if(len == 5){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return LocalTime.parse(date, formatter);
        }
        throw new IllegalArgumentException("Input is not a valid format");
    }

    /**
     *
     * @param fileName
     * @throws IOException
     */
    public void writeToFile(String fileName) throws IOException {
        OutputStream file = new FileOutputStream(fileName);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        output.writeObject(talks);
        output.close();
    }

    /**
     *
     * @param fileName the name of the file that needs to be read from
     * @return a list of Talks that have been read from the file
     * @throws IOException
     * @throws ClassNotFoundException
     */
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

    /**
     * Finds and returns the Entities.Talk object that has the provided id
     * @param talk the id of the Entities.Talk that is desired
     * @return a Entities.Talk that has the id provided
     */
    public Talk findTalk(int talk){
        for(Talk t: talks){
            if (t.getId() == talk) {
                return t;
            }
        }
        return null;
    }

}
