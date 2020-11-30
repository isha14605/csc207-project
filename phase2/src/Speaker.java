import java.util.ArrayList;

/**
 * Represents a Speaker. This is a subclass of the User class.
 * @author Anushka Saini
 * @version 1.0
 * @see User
 */

public class Speaker extends User{
    private ArrayList<String> talksSpeaking;

    /**
     * Creates a Speaker with the provided name, password, and email.
     * @param name the name of this Speaker.
     * @param password the password of this Speaker.
     * @param email the email of this Speaker.
     */
    // Constructor Method for Speaker
    Speaker(String name, String password, String email) {
        super(name, password, email);
        this.talksSpeaking = new ArrayList<String>();
    }

    /**
     * Returns an ArrayList of Talks that holds all of the Talks that the Speaker is speaking at.
     * @return a list of all Talks that the Speaker is speaking at.
     * @see Talk
     */
    // Getter: Retrieves a List of Talks that the Speaker is talking at.
    public ArrayList<String> getTalksSpeaking(){
        return this.talksSpeaking;
    }

    /**
     * Adds a Talk to the Speaker's list of Talks that they are talking at.
     * @param talk the Talk that the Speaker would like to speak at.
     * @see Talk
     */
    // Setter: Add a talk that this Speaker is talking at to the list talks_speaking.
    protected void addTalk(String talk){
        this.talksSpeaking.add(talk);
    }

    /**
     * Returns a string that represents the type of User this object is, eg. S for Speaker.
     * @return a string representing the type of User this object is
     */
    protected char userType(){
        return 'S';
    }
}
