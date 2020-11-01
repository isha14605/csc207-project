public class Talk {
    /* Form: yyyy-mm-dd */
    private String date;
    /* Form: hh-mm */
    private String startTime;
    private String endTime;
    private Speaker speaker;
    private Event event;

    /* Call this constructor only if this will be the only talk of an event. i.e., each room has at most
    one speaker at any given time. */
    public Talk(String date, String startTime, String endTime, Event event)
    {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.event = event;
        this.speaker = null;
    }

    /* getters */
    public String getDate() {return this.date;}
    public String getEndTime() {return this.endTime;}
    public String getStartTime() {return this.startTime;}
    public Speaker getSpeaker() {return this.speaker;}
    public Event getEvent() {return this.event;}

    /* call this method to set the speaker for this talk provided this speaker is not talking at another event
    at the same time. */
    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }
}
