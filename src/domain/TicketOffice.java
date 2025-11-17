package domain;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class TicketOffice implements Serializable{
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
            autosave();
    }

    public void addCustomer(Customer customer){
        customers.add(customer);
        autosave();
    }

    public void addEvent(String eventName, String eventDate, int eventTime, String eventType){
        events.add(new Event(eventName, eventDate, eventTime, eventType));
        autosave();
    }

    public void addEvent(Event event){
        events.add(event);
        autosave();
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

    public boolean sellTickets(Event event, Location location, Customer customer, int qty) {

    // 1. Verificar que la localidad pertenece al evento
        if (!event.getLocations().contains(location)) {
            throw new IllegalArgumentException("La localidad no pertenece al evento.");
        }

    // 2. Verificar disponibilidad
        if (location.getAvailableSeats() < qty) {
            return false; // no alcanza, la UI mostrará mensaje
        }

        // 3. Crear los tickets uno por uno usando sellTicket()
        for (int i = 0; i < qty; i++) {
            sellTicket(event, customer, location);
        }
        autosave();
        return true; // compra exitosa
    }

    private void autosave() {
        try {
            String basePath = System.getProperty("user.dir");
            String path = basePath + File.separator + "ticketoffice.dat";
            data.TicketOfficeStorage.save(this, path);
        } catch (Exception e) {
            System.out.println("Error al autoguardar: " + e.getMessage());
        }
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
