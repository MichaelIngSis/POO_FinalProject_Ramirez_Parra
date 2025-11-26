package domain;

import java.io.*;
/**
 * Represents a single ticket entity for an event purchase.
 * This class links the ticket number, assigned seat, specific location
 * the purchasing customer, and the event itself
 */
public class Ticket implements Serializable{

    private int ticketNumber;
    private int ticketSeat;
    private Location location;
    private Customer customer;
    private Event event;

    //Initializess a new ticket, generating a unique ID and linking all associated event, location, and customer data.
    public Ticket(Event event, Location location, Customer customer, int ticketSeat){
        this.ticketNumber = event.generateTicketNumber();
        this.ticketSeat = ticketSeat;
        this.location = location;
        this.event = event;
        this.customer = customer;
    }

    //The following methods return the properties of the ticket.
    public int getTicketNumber(){return ticketNumber;}
    public int getTicketSeat(){return ticketSeat;}
    public Event getEvent(){return event;}
    public Location getLocation(){return location;}
    public Customer getCustomer(){return customer;}
}
