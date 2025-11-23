package domain;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

import data.CSVEncoder;

public class TicketOffice implements Serializable{
    private int eventCounter = 3;
    private int venueCounter = 1;
    private int ticketOfficeNit;
    private String ticketOfficeAddress;
    private String ticketOfficeEmail;
    private String ticketOfficePhoneNumber;
    private String ticketOfficeCity;
    private List<Customer> customers;
    private List<Event> events;
    private List<Ticket> ticketsRegister;
    private List<Venue> venues;

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
        this.venues = new LinkedList<>();
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

    public void addEvent(String eventName, String eventDate, int eventTime, String eventType, Venue venue){
        events.add(new Event(eventCounter++, eventName, eventDate, eventTime, eventType, venue));
        autosave();
    }

    public void addEvent(Event event){
        events.add(event);
        autosave();
    }

    //Sell ticket para comprar uno por uno
    public Ticket sellTicket(Event event, Customer customer, Location location){
        if(!event.getLocations().contains(location)){
            throw new IllegalArgumentException("Esta localidad no pertenece al evento!");
        }
        if(!location.hasAvailability()){
            throw new IllegalStateException("No hay más asientos disponibles en: " + location.getLocationName());
        }
        
        int seatNumber = location.assignSeat();

        Ticket ticket = new Ticket(event, location, customer, seatNumber);
        
        ticketsRegister.add(ticket);
        event.addTicket(ticket);
        customer.saveTicket(ticket);
        
        return ticket;
    }

    //Sell ticket que se le pasa al frontend y hace toda la compra de los tickets
    public boolean sellTickets(Event event, Location location, Customer customer, int qty) {

    // 1. Verificar que la localidad pertenece al evento
        if (!event.getLocations().contains(location)) {
            throw new IllegalArgumentException("La localidad no pertenece al evento.");
        }

    // 2. Verificar disponibilidad
        if (location.getAvailableSeats() < qty) {
            throw new IllegalStateException("No hay suficientes asientos disponibles en: " + location.getLocationName()); // no alcanza, la UI mostrará mensaje
        }

        // 3. Crear los tickets uno por uno usando sellTicket()
        for (int i = 0; i < qty; i++) {
            sellTicket(event, customer, location);
        }
        autosave();
        return true; // compra exitosa
    }

    public void autosave() {
        try {
            String basePath = System.getProperty("user.dir");
            String path = basePath + File.separator + "ticketoffice.dat";
            data.TicketOfficeStorage.save(this, path);
        } catch (Exception e) {
            System.out.println("Error al autoguardar: " + e.getMessage());
        }
    }

    public void addVenue(String name, String address, int capacity, String characteristic){
        venues.add(new Venue(venueCounter++, name, address, capacity, characteristic));
        autosave();
    }

    public void addLocationToEvent(Event event, String name, int capacity) {
        if (event == null) {
            throw new IllegalArgumentException("Evento no encontrado");
        } 
        event.createLocations(name, capacity);   // modificar el evento
        autosave();            // ← AUTOGUARDADO AQUÍ
    }


    public void updateLocationOfEvent(Event ev, Location loc, String newName, int newCapacity) {
        ev.updateLocation(loc, newName, newCapacity);
        autosave();
    }

    public void removeLocationFromEvent(Event ev, Location loc) {
        ev.removeLocation(loc);
        autosave();
    }


    public boolean removeVenue(int venueId) {

        Venue venueToRemove = null;

    // Buscar el venue por ID
        for (Venue v : venues) {
            if (v.getVenueId() == venueId) {
                venueToRemove = v;
                break;
            }
        }

        if (venueToRemove == null) {
            return false;   // No existe
        }

    // 1. Validar si un evento lo está usando
        for (Event e : events) {
            if (e.getVenue() != null && e.getVenue().getVenueId() == venueId) {

            // 1.1 Si el evento tiene localidades creadas, bloquear eliminación
            if (!e.getLocations().isEmpty()) {
                System.out.println("No se puede eliminar: venue está asignado a evento " 
                                   + e.getEventName() + " con localidades existentes.");
                return false;
            }

            // 1.2 Si el evento tiene tickets vendidos, bloquear
            if (!e.getTickets().isEmpty()) {
                System.out.println("No se puede eliminar: venue está asignado a evento " 
                                   + e.getEventName() + " con tickets vendidos.");
                return false;
            }

            // Si no tiene localidades ni tickets → podemos desvincularlo
                e.setVenue(null);
            }
        }

    // 2. Eliminar de la lista de venues
        venues.remove(venueToRemove);

    // 3. Autoguardar cambios
        autosave();

        return true;
    }

    public boolean removeEvent(int eventId) {
        Event toRemove = null;

        for (Event ev : events) {
            if (ev.getEventId() == eventId) {
                toRemove = ev;
                break;
            }
        }

        if (toRemove != null) {
            events.remove(toRemove);
            autosave(); // ⬅ autoguardado inmediato
            return true;
        }

        return false; // No encontrado
    }


    public boolean removeCustomer(int id) {
        Customer target = null;

        // Buscar el cliente
        for (Customer c : customers) {
            if (c.getCustomerId() == id) {
                target = c;
                break;
            }
        }
        if (target != null) {
            customers.remove(target);
            autosave();
            return true;
            
        }

        return false;
    }


    public void encoderEvents(List<Event> listEvents){
        CSVEncoder<Event> encoder = new CSVEncoder<>(){
            @Override
            public String[] getFieldNames() {
                return new String[]{
                    "eventId",
                    "eventName",
                    "eventDate",
                    "eventTime",
                    "eventType",
                    "eventVenue"
                };
            }
            @Override
            public String[] getValues(Event event) {
                return new String[]{
                    String.valueOf(event.getEventId()),
                    event.getEventName(),
                    event.getEventDate(),
                    String.valueOf(event.getEventTime()),
                    event.getEventType(),
                    event.getVenue().getVenueName()
                };
            }
            @Override
            public String getListName() {
                return "Events";
            }
        };
        saveCSV("CSVEvents.csv",encoder.encode(listEvents));
    }

    public void encoderCustomer(List<Customer> listCustomers){
        CSVEncoder<Customer> encoder = new CSVEncoder<>(){
            @Override
            public String[] getFieldNames() {
                return new String[]{
                    "customerId",
                    "customerName",
                    "customerLastname",
                    "customerAddress",
                    "customerEmail",
                    "customerPhoneNumber"
                };
            }
            @Override
            public String[] getValues(Customer customer) {
                return new String[]{
                    String.valueOf(customer.getCustomerId()),
                    customer.getCustomerName(),
                    customer.getCustomerLastname(),
                    customer.getCustomerAddress(),
                    customer.getCustomerEmail(),
                    customer.getCustomerPhoneNumber()
                };
            }
            @Override
            public String getListName() {
                return "Customers";
            }
        };
        saveCSV("CSVCustomers.csv",encoder.encode(listCustomers));
    }

    public void encoderVenues(List<Venue> listVenues){
        CSVEncoder<Venue> encoder = new CSVEncoder<>(){
            @Override
            public String[] getFieldNames() {
                return new String[]{
                    "venueId",
                    "venueName",
                    "venueAddress",
                    "venueCapacity",
                    "venueCharacteristic"
                };
            }
            @Override
            public String[] getValues(Venue venue) {
                return new String[]{
                    String.valueOf(venue.getVenueId()),
                    venue.getVenueName(),
                    venue.getVenueAddress(),
                    String.valueOf(venue.getVenueCapacity()),
                    venue.getVenueCharacteristic()
                };
            }
            @Override
            public String getListName() {
                return "Venues";
            }
        };
        saveCSV("CSVVenues.csv",encoder.encode(listVenues));
    }

    public static void saveCSV(String fileName, String csvContent){
        try(FileWriter writer = new FileWriter(fileName)){
            writer.write(csvContent);
        }catch(IOException e){
            System.err.println("Error al crear el archivo csv: " + e.getMessage());
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
    public List<Venue> getVenues(){return venues;}
}
