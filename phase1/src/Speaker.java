import java.util.ArrayList;

public class Speaker extends User{
    private ArrayList<Talk> talksSpeaking;

    // Constructor Method for Speaker
    Speaker(String name, String password, String email) {
        super(name, password, email);
        this.talksSpeaking = new ArrayList<Talk>();
    }

    // Getter Method for the List of Talks that the Speaker is talking at.
    public ArrayList<Talk> getTalksSpeaking(){
        return this.talksSpeaking;
    }

    // Add a talk that this Speaker is talking at to the list talks_speaking.
    protected void addTalk(Talk talk){
        this.talksSpeaking.add(talk);
    }

    protected char userType(){
        return 'S';
    }
}
