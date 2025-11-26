package domain;

import java.io.*;

/**
 * Represents a specific section or area within a larger event venue
 * This class defines the name and maximum capacity for a given location
 */

public class Location implements Serializable{
    private String locationName;
    private int locationCapacity; 
    private int availableSeats;
    private int nextSeatNumber;

    //Constructor to initialize a Location object with capacity and seat tracking data
    public Location(String locationName, int locationCapacity){
        this.locationName = locationName;
        this.locationCapacity = locationCapacity;
        this.availableSeats = locationCapacity;
        this.nextSeatNumber = 1;
    }

    //Checks if there are any available seats remaining in this location
    public boolean hasAvailability(){
        return availableSeats > 0;
    }

 
    //Assign the next available seat number, reduces the available seat count, and validates availability first.
    public int assignSeat(){
        if(!hasAvailability()){
            throw new IllegalStateException("No seats available in location: " + locationName);
        }
        int assignedSeat = nextSeatNumber;
        nextSeatNumber++;
        availableSeats--;

        return assignedSeat;
    }

    //Updates the location name if the new name provided is non-empty
    public void setLocationName(String locationName){
        if(!locationName.equals("")){
            this.locationName = locationName;
        }
    }

    //Updates the location capacity
    public void setLocationCapacity(int locationCapacity){
        this.locationCapacity = locationCapacity;
    }

    //The following methos return the properties of the location.
    public String getLocationName(){return locationName;}
    public int getLocationCapacity(){return locationCapacity;}
    public int getAvailableSeats(){return availableSeats;}

    //Generates a display string for the location showing name and capacity
    @Override
    public String toString() {
        return locationName + " - " + locationCapacity ;
    }
}
