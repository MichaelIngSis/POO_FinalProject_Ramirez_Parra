package domain;

import java.io.*;
/**
 * Entity class that defines the physical location, capacity, and details of an event venue.
 */
public class Venue implements Serializable{

    private int venueId;
    private String venueName;
    private String venueAddress;
    private int venueCapacity;
    private String venueCharacteristic;

    //Initializes the venue details, applying validation logic for the venue´s characteristic field
    public Venue(int venueId, String venueName, String venueAddress, int venueCapacity, 
            String venueCharacteristic){
        this.venueId = venueId;
        this.venueName = venueName;
        this.venueAddress = venueAddress;
        this.venueCapacity = venueCapacity;
        if(venueCharacteristic.equals("Eventos de Música") || venueCharacteristic.equals("Eventos de Vehiculos") 
        || venueCharacteristic.equals("Eventos Deportivos") || venueCharacteristic.equals("Multiples Eventos")){
            this.venueCharacteristic = venueCharacteristic;
        }else{
            this.venueCharacteristic = "No especificada";
        }
    }

    //Update venue name if new name is not empty
    public void setVenueName(String newVenueName){
        if(!newVenueName.equals("")){
            venueName = newVenueName;
        }
    }

    //Update venue characteristic, applying validation logic for new characteristic.
    public void setVenueCharacteristic(String newVenueCharacteristic){
        if(newVenueCharacteristic.equals("Eventos de Música") || newVenueCharacteristic.equals("Eventos de Vehiculos") 
        || newVenueCharacteristic.equals("Eventos Deportivos") || newVenueCharacteristic.equals("Multiples Eventos")){
            venueCharacteristic = newVenueCharacteristic;
        }
    }
    
    //Update venue address
    public void setVenueAddress(String newVenueAddress){
        venueAddress = newVenueAddress;
    }

    //Update venue capacity
    public void setVenueCapacity(int newVenueCapacity){
        venueCapacity = newVenueCapacity;
    }

    //The following methods return the properties of the venue
    public int getVenueId(){return venueId;}
    public String getVenueName(){return venueName;}
    public String getVenueAddress(){return venueAddress;}
    public int getVenueCapacity(){return venueCapacity;}
    public String getVenueCharacteristic(){return venueCharacteristic;}

    //Generates a display string for the venue showing ID, name, and characteristic
    @Override
    public String toString() {
        return venueId + " - " + venueName + " (" + venueCharacteristic + ")";
    }
}
