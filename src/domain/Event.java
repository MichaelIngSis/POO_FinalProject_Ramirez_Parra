package domain;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Event implements Serializable{
    private static int eventCounter = 1;
    private int eventId;
    private String eventName;
    private String eventDate;
    private int eventTime;
    private String eventType;
    private Venue venue;
    private List<Ticket> tickets;
    private List<Location> locations;

    public Event(String eventName, String eventDate, int eventTime, String eventType){
        this.eventId = eventCounter ++;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventType = eventType;
        this.locations = new LinkedList<>();
        this.tickets = new LinkedList<>();
    }

    public void setEventName(String newEventName){
        if(!newEventName.equals("")){
            eventName = newEventName;
        }
    }
    
    public void setEventDate(String newEventDate){
        if(!newEventDate.equals("")){
            eventDate = newEventDate;
        }
    }

    public void setEventTime(int newEventTime){
        if(newEventTime > 0 && newEventTime < 2400){
            eventTime = newEventTime;
        }
    }

    public void setEventType(String newEventType){
        if(!newEventType.equals("")){
            eventType = newEventType;
        }
    }

    public void setVenue(Venue venue){
        this.venue = venue;
    }

    /*public void createLocations(Location loc){
        if(isLocationCapacityValid(loc, loc.getLocationCapacity())){
            locations.add(loc);
        }
    }*/

    public void createLocations(String locationName, int locationCapacity){
        if(isLocationCapacityValid(locationCapacity)){
            locations.add(new Location(locationName, locationCapacity));
        }
    }

    public boolean isLocationCapacityValid(int newCapacity){
        int totalLocationCapacity = 0;
        if(locations != null){
            for (Location location : locations){
                    totalLocationCapacity += location.getLocationCapacity();
            }
        }
        return (totalLocationCapacity + newCapacity <= venue.getVenueCapacity()) ? true : false;
    }
    
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

    public void updateLocation(Location loc, String newName, int newCapacity) {
        if(isLocationCapacityValid(loc,newCapacity)){
            loc.setLocationName(newName);
            loc.setLocationCapacity(newCapacity);
        }
    }

    public void removeLocation(Location loc){
        locations.remove(loc);
    }

    public List<Location> getLocations(){
        return locations;
    }

    public void addTicket(Ticket ticket){
        tickets.add(ticket);
    }

    @Override
    public String toString() {
        return eventId + " - " + eventName + " (" + eventDate + ")";
    }

    public List<Ticket> getTickets(){return tickets;}
    public int getEventId(){return eventId;}
    public String getEventName(){return eventName;}
    public String getEventDate(){return eventDate;}
    public int getEventTime(){return eventTime;}
    public String getEventType(){return eventType;}
    public Venue getVenue(){return venue;}
}
