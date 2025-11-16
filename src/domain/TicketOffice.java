package domain;
import java.util.LinkedList;
import java.util.List;

public class TicketOffice {
    private int ticketOfficeNit;
    private String ticketOfficeAddress;
    private String ticketOfficeEmail;
    private String ticketOfficePhoneNumber;
    private String ticketOfficeCity;
    private List<Customer> customers;
    private List<Event> events;
    private List<Ticket> ticketsRegister;

    public TicketOffice(int ticketOfficeNit, String ticketOfficeAddress, String ticketOfficeEmail, String ticketOfficePhoneNumber,
            String ticketOfficeCity){
        this.ticketOfficeNit = ticketOfficeNit;
        this.ticketOfficeAddress = ticketOfficeAddress;
        this.ticketOfficeEmail = ticketOfficeEmail;
        this.ticketOfficePhoneNumber = ticketOfficePhoneNumber;
        this.ticketOfficeCity = ticketOfficeCity;
        this.customers = new LinkedList<>();
        this.events = new LinkedList<>();
        this.ticketsRegister = new LinkedList<>();
    }

    public void setTicketOfficeAddress(String newTicketOfficeAddress){
        if(!newTicketOfficeAddress.equals("")){
            ticketOfficeAddress = newTicketOfficeAddress;
        }
    }

    public void setTicketOfficeEmail(String newTicketOfficeEmail){
        if(!newTicketOfficeEmail.equals("")){
            ticketOfficeEmail = newTicketOfficeEmail;
        }
    }

    public void setTicketOfficePhoneNumber(String newTicketOfficePhoneNumber){
        if(!newTicketOfficePhoneNumber.equals("")){
            ticketOfficePhoneNumber = newTicketOfficePhoneNumber;
        }
    }

    public void setTicketOfficeCity(String newTicketOfficeCity){
        if(!newTicketOfficeCity.equals("")){
            ticketOfficeCity = newTicketOfficeCity;
        }
    }

    public void addCustomer(int customerId, String customerName, String customerLastname,
                    String customerAddress, String customerEmail, String customerPhoneNumber){
            customers.add(new Customer(customerId, customerName, customerLastname, customerAddress, customerEmail, customerPhoneNumber));
    }

    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    public void addEvents(String eventName, String eventDate, int eventTime, String eventType){
        events.add(new Event(eventName, eventDate, eventTime, eventType));
    }

    public void addEvents(Event event){
        events.add(event);
    }

    public Ticket sellTicket(Event event, Customer customer, Location location){
        if(!event.getLocations().contains(location)){
            throw new IllegalArgumentException("Esta localidad no pertenece al evento!");
        }
        if(!location.hasAvailability()){
            throw new IllegalStateException("No hay más asientos ddisponibles en: " + location.getLocationName());
        }
        
        int seatNumber = location.assignSeat();

        Ticket ticket = new Ticket(event, location, customer, seatNumber);
        
        ticketsRegister.add(ticket);
        event.addTicket(ticket);
        customer.saveTicket(ticket);
        
        return ticket;
    }

    public List<Ticket> getTickets(){return ticketsRegister;}
    public int getTicketOfficeNit(){return ticketOfficeNit;}
    public String getTicketOfficeAddress(){return ticketOfficeAddress;}
    public String getTicketOfficeEmail(){return ticketOfficeEmail;}
    public String getTicketOfficePhoneNumber(){return ticketOfficePhoneNumber;}
    public String getTicketOfficeCity(){return ticketOfficeCity;}
    public List<Customer> getCustomers(){return customers;}
    public List<Event> getEvents(){return events;}
}
