import java.util.ArrayList;
import java.util.Map;

// This class is NOT complete yet. Will take another look at it in a while.

public class Speaker extends User{
    private ArrayList<String> talks_speaking;

    // Constructor Method for Speaker
    Speaker(String name, String password, String email) {
        super(name, password, email);
        this.talks_speaking = new ArrayList<String>();
    }

    // Getter Method for the List of Talks that the Speaker is talking at.
    public ArrayList<String> getTalks_speaking(){
        return this.talks_speaking;
    }

    // Add a talk that this Speaker is talking at to the list talks_speaking.
    protected void add_talk(String talk){
        this.talks_speaking.add(talk);
    }
}
