package Entities;

import java.util.ArrayList;

/**
 * Represents a Entities.VIP user. This is a subclass of the Entities.Attendee class.
 * @author Anushka Saini
 * @version 1.0
 * @see User
 * @see Attendee
 */
public class VIP extends Attendee{
    private ArrayList<Integer> vipEventsAttending;
    private String memberStatus; // Determines the member's fame based on points (events attended)
    private int memberPoints; // Stores a cumalative count of the VIP's points, updates each time new event attended

    /**
     * Creates a Entities.VIP with the provided name, password, and email.
     * @param name the name of this Entities.VIP.
     * @param password the password of this Entities.VIP.
     * @param email the email of this Entities.VIP.
     */
    // Constructor for Entities.VIP
    public VIP(String name, String password, String email) {
        super(name, password, email);
        this.vipEventsAttending = new ArrayList<Integer>();
        this.memberPoints = 0;
        this.memberStatus = 'Bronze';
    }

    /**
     *
     * @return an arraylist of the vip-only events that the Entities.VIP is schedulled to attend
     */
    public ArrayList<Integer> getVipEventsAttending(){
        return this.vipEventsAttending;
    }

    /**
     * Adds the particular Entities.VIP event to the list of Entities.VIP events that the user is attending
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

    public int getMemberPoints(){
        return this.memberPoints;
    }

    public void addPoints(int newPoints){
        this.memberPoints += newPoints
    }

    public String getMemberStatus(){
        return this.memberStatus;
    }

    // Double check on where to implement this method <- member determination with points
    public void setMemberStatus(int memberPoints){
        if (memberPoints >= 1000){
            this.memberStatus = "Platinum";
        } else if (memberPoints < 1000 && points >= 500){
            this.memberStatus = "Gold";
        } else if (memberPoints < 500 && points >= 100){
            this.memberStatus = "Silver";
        } else if (memberPoints < 100){
            // Just in case we implement a method that requires us to deduct points from a VIP user
            this.memberStatus = "Bronze";
        }
    }

    public char userType(){
        return 'V';
    }
}
