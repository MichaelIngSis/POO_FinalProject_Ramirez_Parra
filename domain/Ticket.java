public class Ticket {
    private int ticketNumber;
    private int ticketSeat;
    private String ticketLocation;
    private Customer customer;

    public Ticket(int ticketNumber, int ticketSeat, String ticketLocation){
        this.ticketNumber = ticketNumber;
        this.ticketSeat = ticketSeat;
        this.ticketLocation = ticketLocation;
    }

    public int getTicketNumber(){return ticketNumber;}
    public int ticketSeat(){return ticketSeat;}
    public String ticketLocation(){return ticketLocation;}
}
