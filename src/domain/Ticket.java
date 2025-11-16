package domain;
public class Ticket {

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
    public int getCustomerId(){return customer.getCustomerId();}
    public String getCustomerName(){return customer.getCustomerName();}
}
