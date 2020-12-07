package Entities;

import java.io.Serializable;
import java.util.ArrayList;

// Note: the member status and points system is only applicable to VIP users. This feature is not available to
// regular attendees. VIP is essentially like a members-only club.

/**
 * Represents a Entities.VIP user. This is a subclass of the Entities.Attendee class.
 * @author Anushka Saini
 * @version 1.0
 * @see User
 * @see Attendee
 */
public class VIP extends Attendee implements Serializable {
    private ArrayList<Integer> vipEventsAttending;
    private String memberStatus; // Determines the member's fame based on points (events attended)
    private int memberPoints; // Stores a cumulative count of the VIP's points, updates each time new event attended

    /**
     * Creates a Entities.VIP with the provided name, password, and email.
     * @param name the name of this Entities.VIP.
     * @param password the password of this Entities.VIP.
     * @param email the email of this Entities.VIP.
     */
    public VIP(String name, String password, String email) {
        super(name, password, email);
        this.vipEventsAttending = new ArrayList<Integer>();
        this.memberPoints = 0;
        this.memberStatus = "Bronze"; // Every VIP starts at the Bronze Level
    }

    /**
     * Returns an ArrayList of integers that represent ids of vip-only events that the Entities.VIP is attending.
     * @return an arraylist of vip-only events' ids that the Entities.VIP is scheduled to attend
     */
    public ArrayList<Integer> getVipEventsAttending(){
        return this.vipEventsAttending;
    }

    /**
     * Adds the vip-only event's id of the event that the Entities.VIP is attending.
     * @param event is the id of the event the Entities.VIP wants to attend
     */
    public void attendVipEvent(Integer event){
        this.vipEventsAttending.add(event);
    }

    /**
     * Removes Entities.VIP event that this user is attending
     * @param event is the id of the Entities.VIP event the user no longer wishes to attend
     */
    public void removeVipEvent(Integer event) {
        this.vipEventsAttending.remove(event);
    }

    /**
     * Returns number of points that the Entities.VIP has
     * @return the number of member points the Entities.VIP has currently
     */
    public int getMemberPoints(){
        return this.memberPoints;
    }

    /**
     * Increments the Entities.VIP's member points by pointsToAdd
     * @param pointsToAdd the number of points to add to the Entities.VIP 's member points total
     */
    // Use this method to add points when a VIP adds an event to attend
    public void addPoints(int pointsToAdd){
        this.memberPoints += pointsToAdd;
    }

    /**
     * Decrements the Entities.VIP's member points by pointsToRemove
     * @param pointsToRemove the number of points to remove from the Entities.VIP 's member points total
     */
    // Use this method to deduct points when a VIP cancels an event to attend
    public void removePoints(int pointsToRemove){
        this.memberPoints -= pointsToRemove;
    }

    /**
     * Sets the Entities.VIP 's member status
     * @param newStatus the status that the Entities.VIP is being changed to
     */
    public void setMemberStatus(String newStatus){this.memberStatus = newStatus;}

    /**
     * Returns the Entities.VIP 's current member status
     * @return the Entities.VIP 's current status level, either 'Bronze', 'Silver', 'Gold', or 'Platinum'
     */
    public String getMemberStatus(){
        return this.memberStatus;
    }

    /**
     * Returns a string that represents the type of Entities.User this object is, eg. V for Entities.VIP.
     * @return a string representing the type of Entities.User this object is
     */
    public char userType(){
        return 'V';
    }
}
