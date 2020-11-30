import java.util.ArrayList;

/**
 * Represents a VIP user. This is a subclass of the Attendee class.
 * @author Anushka Saini
 * @version 1.0
 * @see User
 * @see Attendee
 */
public class VIP extends Attendee{
    private ArrayList<Integer> vipEventsAttending;

    /**
     * Creates a VIP with the provided name, password, and email.
     * @param name the name of this VIP.
     * @param password the password of this VIP.
     * @param email the email of this VIP.
     */
    // Constructor for VIP
    VIP(String name, String password, String email) {
        super(name, password, email);
        this.vipEventsAttending = new ArrayList<Integer>();
    }

    /**
     *
     * @return an arraylist of the vip-only events that the VIP is schedulled to attend
     */
    public ArrayList<Integer> getVipEventsAttending(){
        return this.vipEventsAttending;
    }

    protected void attendVipEvent(Integer event){
        this.vipEventsAttending.add(event);
    }

    protected void removeEvent(Integer event) {
        this.vipEventsAttending.remove(event);
    }

    protected char userType(){
        return 'V';
    }
}
