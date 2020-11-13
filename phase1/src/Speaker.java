import java.util.ArrayList;
import java.util.Map;

public class Speaker extends User{
    private ArrayList<Talk> talks_speaking;

    // Constructor Method for Speaker
    Speaker(String name, String password, String email) {
        super(name, password, email);
        this.talks_speaking = new ArrayList<Talk>();
    }

    // Getter Method for the List of Talks that the Speaker is talking at.
    public ArrayList<Talk> getTalks_speaking(){
        return this.talks_speaking;
    }

    // Add a talk that this Speaker is talking at to the list talks_speaking.
    protected void add_talk(Talk talk){
        this.talks_speaking.add(talk);
    }

    protected char userType(){
        return 'S';
    }
}
