import java.time.LocalDateTime;

public class Talk {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Speaker speaker;
    private Event event;

    /* Call this constructor only if there's no overlapping between talks of a particular event and
    this talk happens 9-5. */
    public Talk(LocalDateTime startTime, LocalDateTime endTime, Event event)
    {
        this.startTime = startTime;
        this.endTime = endTime;
        this.event = event;
        this.speaker = null;
    }

    /* getters */
    public LocalDateTime getEndTime() {return this.endTime;}
    public LocalDateTime getStartTime() {return this.startTime;}
    public Speaker getSpeaker() {return this.speaker;}
    public Event getEvent() {return this.event;}

    /* call this method to set the speaker for this talk provided this speaker is not talking at another event
    at the same time. */
    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }
}
