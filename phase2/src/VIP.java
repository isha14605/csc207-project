import java.util.ArrayList;

/*
Potential idea to discuss: a point system for VIPs, the more vip-only events they attend the more points they get?
or something along those lines to add some complexity
 */

public class VIP extends Attendee{
    private ArrayList<Event> vipEventsAttending;

    // Constructor for VIP
    VIP(String name, String password, String email) {
        super(name, password, email);
        this.vipEventsAttending = new ArrayList<Event>();
    }

    public ArrayList<Event> getVipEventsAttending(){
        return this.vipEventsAttending;
    }

    protected void attendVipEvent(Event event){
        this.vipEventsAttending.add(event);
    }

    protected void removeEvent(Event event) {
        this.vipEventsAttending.remove(event);
    }

    protected char userType(){
        return 'V';
    }
}
