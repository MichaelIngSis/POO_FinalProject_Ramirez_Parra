package domain;

import java.io.*;

public class Ticket implements Serializable{

    private static int globalCounter = 1;
    private int ticketNumber;
    private int ticketSeat;
    private Location location;
    private Customer customer;
    private Event event;

    public Ticket(Event event, Location location, Customer customer, int ticketSeat){
        this.ticketNumber = globalCounter ++;
        this.ticketSeat = ticketSeat;
        this.location = location;
        this.event = event;
        this.customer = customer;
    }



    public int getTicketNumber(){return ticketNumber;}
    public int getTicketSeat(){return ticketSeat;}
    public Event getEvent(){return event;}
    public Location getLocation(){return location;}
    public Customer getCustomer(){return customer;}
}
