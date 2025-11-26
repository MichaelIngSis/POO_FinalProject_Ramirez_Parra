package domain;
import java.io.*;
import java.util.LinkedList;
import java.util.List;
/**
 * Represents an event within the ticket office management system
 * This class handles event details, location management, capacity validation
 * and ticket generation/storage.
 */
 
public class Event implements Serializable{
    private int eventId;
    private String eventName;
    private String eventDate;
    private int eventTime;
    private String eventType;
    private Venue venue;
    private int ticketCounter = 1;
    private List<Ticket> tickets;
    private List<Location> locations;
    /**
    *Constructs a new Event object with the specified details.
    *Initializes empty lists for locations and tickets.
    */
    public Event(int eventId, String eventName, String eventDate, int eventTime, String eventType, Venue venue){
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventType = eventType;
        this.locations = new LinkedList<>();
        this.tickets = new LinkedList<>();
        this.venue = venue;
    }

    //Updates the event name if the new name is valid.
    public void setEventName(String newEventName){
        if(!newEventName.equals("")){
            eventName = newEventName;
        }
    }
    
    //Updates the event date if the new date is valid.
    public void setEventDate(String newEventDate){
        if(!newEventDate.equals("")){
            eventDate = newEventDate;
        }
    }

    //Updates the event time if the new time is valid
    public void setEventTime(int newEventTime){
        if(newEventTime > 0 && newEventTime < 2400){
            eventTime = newEventTime;
        }
    }

    //Updates the event type if the new type is not empty.
    public void setEventType(String newEventType){
        if(!newEventType.equals("")){
            eventType = newEventType;
        }
    }

    //Update event venue.
    public void setVenue(Venue venue){
        this.venue = venue;
    }

    //Creates a new location if the capacity is valid, otherwise throws an exception
    public void createLocations(String locationName, int locationCapacity, int locationPrice){
        if(!isLocationCapacityValid(locationCapacity)){
            throw new IllegalArgumentException("No se puede agregar la localidad. Capacidad Excedida");
        }
        locations.add(new Location(locationName, locationCapacity, locationPrice));
    }

    
    //Validates if the tocal capacity of all locations, plus a new capacity value, fits within the venue´s capacity limit
    public boolean isLocationCapacityValid(int newCapacity){
        int totalLocationCapacity = 0;
        if(locations != null){
            for (Location location : locations){
                    totalLocationCapacity += location.getLocationCapacity();
            }
        }
        return (totalLocationCapacity + newCapacity <= venue.getVenueCapacity()) ? true : false;
    }
    
    //Validates a capacity change (for existing locations) against the venue´s total capacity, excluding the provided 'loc'
    //from the current calculation
    public boolean isLocationCapacityValid(Location loc, int newCapacity){
        int totalLocationCapacity = 0;
        if(locations != null){
            for (Location location : locations){
                if(loc == location){
                }else{
                    totalLocationCapacity += location.getLocationCapacity();
                }
            }
        }
        return (totalLocationCapacity + newCapacity <= venue.getVenueCapacity()) ? true : false;
    }

    //Updates an existing location´s details after verifying the new capacity is valid within the venue limits
    public void updateLocation(Location loc, String newName, int newCapacity) {
        if(!isLocationCapacityValid(loc,newCapacity)){
            throw new IllegalArgumentException("No se puede hacer el cambio. Capacidad excedida");
        }
        loc.setLocationName(newName);
        loc.setLocationCapacity(newCapacity);
    }

    //Removes the specified location from the event´s list of locations
    public void removeLocation(Location loc){
        locations.remove(loc);
    }

    //Returns the complete list of locations for an event
    public List<Location> getLocations(){
        return locations;
    }

    //Adds a single ticket object to the event´s list of tickets
    public void addTicket(Ticket ticket){
        tickets.add(ticket);
    }

    //Generates a display string for the event showing ID, name, and date.
    @Override
    public String toString() {
        return eventId + " - " + eventName + " (" + eventDate + ")";
    }

    //Generates and returns the next sequential ticket number, then increments the counter
    public int generateTicketNumber(){
        return ticketCounter ++;
    }

    //The following methods return the properties of the event.
    public List<Ticket> getTickets(){return tickets;}
    public int getEventId(){return eventId;}
    public String getEventName(){return eventName;}
    public String getEventDate(){return eventDate;}
    public int getEventTime(){return eventTime;}
    public String getEventType(){return eventType;}
    public Venue getVenue(){return venue;}
}
