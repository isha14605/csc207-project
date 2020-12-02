import java.util.ArrayList;

/**
 * Represents a Entities.Speaker. This is a subclass of the Entities.User class.
 * @author Anushka Saini
 * @version 1.0
 * @see User
 */

public class Speaker extends User{
    private ArrayList<Talk> talksSpeaking;

    /**
     * Creates a Entities.Speaker with the provided name, password, and email.
     * @param name the name of this Entities.Speaker.
     * @param password the password of this Entities.Speaker.
     * @param email the email of this Entities.Speaker.
     */
    // Constructor Method for Entities.Speaker
    Speaker(String name, String password, String email) {
        super(name, password, email);
        this.talksSpeaking = new ArrayList<Talk>();
    }

    /**
     * Returns an ArrayList of Talks that holds all of the Talks that the Entities.Speaker is speaking at.
     * @return a list of all Talks that the Entities.Speaker is speaking at.
     * @see Talk
     */
    // Getter: Retrieves a List of Talks that the Entities.Speaker is talking at.
    public ArrayList<Talk> getTalksSpeaking(){
        return this.talksSpeaking;
    }

    /**
     * Adds a Entities.Talk to the Entities.Speaker's list of Talks that they are talking at.
     * @param talk the Entities.Talk that the Entities.Speaker would like to speak at.
     * @see Talk
     */
    // Setter: Add a talk that this Entities.Speaker is talking at to the list talks_speaking.
    protected void addTalk(Talk talk){
        this.talksSpeaking.add(talk);
    }

    /**
     * Returns a string that represents the type of Entities.User this object is, eg. S for Entities.Speaker.
     * @return a string representing the type of Entities.User this object is
     */
    protected char userType(){
        return 'S';
    }
}
