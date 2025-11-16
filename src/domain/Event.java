package domain;
import java.util.LinkedList;
import java.util.List;

public class Event {
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
    /* 
    public Event(int eventId, String eventName, String eventDate, int eventTime, String eventType, Venue venue){
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventType = eventType;
        this.venue = venue;
    }*/

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

    public void createLocations(String locationName, int locationCapacity){
        int totalLocationCapacity = 0;
        if(locations != null){
            for(Location location : locations){
                totalLocationCapacity += location.getLocationCapacity();
            }
        }
        
        if(totalLocationCapacity + locationCapacity <= venue.getVenueCapacity()){
            locations.add(new Location(locationName, locationCapacity));
        }
    }

    public List<Location> getLocations(){
        return locations;
    }

    public void addTicket(Ticket ticket){
        tickets.add(ticket);
    }

    public List<Ticket> getTickets(){return tickets;}
    public int getEventId(){return eventId;}
    public String getEventName(){return eventName;}
    public String getEventDate(){return eventDate;}
    public int getEventTime(){return eventTime;}
    public String getEventType(){return eventType;}
}
