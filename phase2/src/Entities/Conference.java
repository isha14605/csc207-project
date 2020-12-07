package Entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Conference implements Serializable {
    private String name;
    private String confDescription;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate confDate;
    private ArrayList<Integer> eventIds;
    private ArrayList<String> eventName;

    // Constructor for Entities.Conference
    /**
     * Creates a Entities.Conference with the specified name, description, start time, end time, date of Entities.Conference and an
     * empty list of event ids.
     * @param name the name of the specified conference
     * @param confDescription the description of the conference
     * @param startTime the time when the conference starts
     * @param endTime the time when the conference ends
     * @param confDate the date when the conference is happening
     */
    public Conference(String name, String confDescription, LocalTime startTime, LocalTime endTime,
                      LocalDate confDate){
        this.name = name;
        this.confDescription = confDescription;
        this.startTime = startTime;
        this.endTime = endTime;
        this.confDate = confDate;
        this.eventIds = new ArrayList<Integer>();
        this.eventName = new ArrayList<>();
    }


    // Getters

    /**
     * Returns a String that is the name of this conference.
     * @return a String that is the name of this conference.
     */
    public String getName() {return name;}

    /**
     * Returns a String that is the description of this conference.
     * @return a String which is the description of this conference.
     */
    public String getConfDescription() {return confDescription;}

    /**
     * Returns a LocalTime that is the start time of this conference.
     * @return a LocalTime that is the start time of this conference.
     */
    public LocalTime getStartTime() {return startTime;}

    /**
     * Returns a LocalTime that is the end time of this conference.
     * @return a LocalTime that is the end time of this conference.
     */
    public LocalTime getEndTime() {return endTime;}

    /**
     * Returns a LocalDate that is the date of this conference.
     * @return a LocalDate that is the date of this conference.
     */
    public LocalDate getConfDate() {return confDate;}

    /**
     * Returns an arraylist that contains String objects that are the names of events in the conference.
     * @return an arraylist that contains String objects that are the names of events in the conference.
     */
    public ArrayList<String> getEventName() {
        return eventName;
    }

    /**
     * Returns an arraylist that contains Integer objects that are the ids of events in the conference.
     * @return an arraylist that contains Integer objects that are the ids of events in the conference.
     */
    public ArrayList<Integer> getEventIds() {
        return eventIds;
    }


    // Setters

    /**
     * Adds an Entities.Event's id to this conference's eventIds list
     * Entities.Conference Manager must ensure that two events running at the same time are not added.
     * @param event an event id that is a part of this conference.
     */
    public void addEvent(Integer event) { eventIds.add(event);}

    /**
     * Adds an Entities.Event's Name to this conference's eventIds list
     * Entities.Conference Manager must ensure that two events running at the same time are not added.
     * @param event an event name that is a part of this conference.
     */
    public void addEventName(String event) { eventName.add(event);}

    /**
     * Removes an Entities.Event's id to this conference's eventIds list
     * @param event the id of the event that is no longer a part of this conference.
     */
    public void removeEvent(Integer event) { eventIds.remove(event);}

    /**
     * Removes name of Entities.Event from the events in the conference list
     * @param event name of the event that is no longer part of this conference
     */
    public  void removeEventName(String event){ eventName.remove(event);}
}
