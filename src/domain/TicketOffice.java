package domain;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

import data.CSVEncoder;

/**
 * Represents the main hub or management system for the entire ticket office operation.
 * This class manages lists of customers, events, venues, and registered tickets.
 * and maintains counter for unique IDs.
 */

public class TicketOffice implements Serializable{
    private int eventCounter = 1;
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

    //Initializes the ticket office main object with contact information and empty data repositories.
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

    //Update ticket office address if new address is not empty
    public void setTicketOfficeAddress(String newTicketOfficeAddress){
        if(!newTicketOfficeAddress.equals("")){
            ticketOfficeAddress = newTicketOfficeAddress;
        }
    }
    
    //Update ticket office email if new email is not empty
    public void setTicketOfficeEmail(String newTicketOfficeEmail){
        if(!newTicketOfficeEmail.equals("")){
            ticketOfficeEmail = newTicketOfficeEmail;
        }
    }

    //Update ticket office phone number if new phone number is not empty
    public void setTicketOfficePhoneNumber(String newTicketOfficePhoneNumber){
        if(!newTicketOfficePhoneNumber.equals("")){
            ticketOfficePhoneNumber = newTicketOfficePhoneNumber;
        }
    }

    //Update ticket office city if new city is not empty
    public void setTicketOfficeCity(String newTicketOfficeCity){
        if(!newTicketOfficeCity.equals("")){
            ticketOfficeCity = newTicketOfficeCity;
        }
    }

    //Add a new customer record to the system´s customer list and triggers an autosave operation.
    public void addCustomer(Customer customer) throws Exception{
        customers.add(customer);
        autosave();
    }

    /**
     * Creates a new Event object with a unique, automatically generated ID and adds it to the event list.
     * Triggers an autosave operation after adding the event
     */
    public void addEvent(String eventName, String eventDate, int eventTime, String eventType, Venue venue) throws Exception{
        events.add(new Event(eventCounter++, eventName, eventDate, eventTime, eventType, venue));
        autosave();
    }

    /**
     * Processes the sale of a new ticket, managing the full transaction lifecycle.
     * Validates that the location belongs to the event and has availability, assigns a seat,
     * records the ticket in all relevant lists (office, event, customer), and returns the new ticket object.
    */
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

    /**
     * Processes the sale of a specified quantity of tickets for a specific event and location
     * Validates that the location belongs to the event and has sufficient availability
     * before processing each individual ticket sale and triggering an autosave.
     */
    public boolean sellTickets(Event event, Location location, Customer customer, int qty) throws Exception {
        if (!event.getLocations().contains(location)) {
            throw new IllegalArgumentException("La localidad no pertenece al evento.");
        }

        if (location.getAvailableSeats() < qty) {
            throw new IllegalStateException("No hay suficientes asientos disponibles en: " + location.getLocationName()); // no alcanza, la UI mostrará mensaje
        }

        for (int i = 0; i < qty; i++) {
            sellTicket(event, customer, location);
        }
        autosave();
        return true;
    }

    //Persissts the current state of the ticket office system data to a local file
    public void autosave() throws Exception{
        String basePath = System.getProperty("user.dir");
        String path = basePath + File.separator + "ticketoffice.dat";
        data.TicketOfficeStorage.save(this, path);
    }

    //Creates, registers, and saves a new venue using the next available venue ID. Throws an exception if saving fails.
    public void addVenue(String name, String address, int capacity, String characteristic) throws Exception{
        venues.add(new Venue(venueCounter++, name, address, capacity, characteristic));
        autosave();
    }

    // Assigns a new location to an existing event, validates the input, and persists the changes to storage
    public void addLocationToEvent(Event event, String name, int capacity) throws Exception{
        if (event == null) {
            throw new IllegalArgumentException("Evento no encontrado");
        } 
        event.createLocations(name, capacity);
        autosave();            
    }

    // Updates the details of a specific location assigned to an event, validates changes, and persists the data.
    public void updateLocationOfEvent(Event ev, Location loc, String newName, int newCapacity) throws Exception{
        ev.updateLocation(loc, newName, newCapacity);
        autosave();
    }

    // Removes a location from an event, but only after verifying that no tickets have been sold for it yet
    public void removeLocationFromEvent(Event ev, Location loc) throws Exception{
        for(Event e : events){
            for (Ticket ticket : e.getTickets()){
                if(ticket.getLocation() == loc){
                    throw new IllegalStateException("Ya hay tickets vendidos para esta localidad");
                }
            }
        }
        ev.removeLocation(loc);
        autosave();
    }


    /**
    * Attempts to remove a venue by its ID after validating several conditions.
    *
    * The venue cannot be removed if:
    * 1. It is currently associated with any event that has existing locations defined.
    * 2. It is currently associated with any event that has tickets already sold.
    * If safe to remove, the venue is deleted and an autosave is triggered.
    */
    public boolean removeVenue(int venueId) throws Exception{
        Venue venueToRemove = null;
        for (Venue v : venues) {
            if (v.getVenueId() == venueId) {
                venueToRemove = v;
                break;
            }
        }
        if (venueToRemove == null) {
            return false;
        }
        for (Event e : events) {
            if (e.getVenue() != null && e.getVenue().getVenueId() == venueId) {
                if (!e.getLocations().isEmpty()) {
                    System.out.println("No se puede eliminar: venue está asignado a evento " 
                                   + e.getEventName() + " con localidades existentes.");
                    return false;
                }
                if (!e.getTickets().isEmpty()) {
                    System.out.println("No se puede eliminar: venue está asignado a evento " 
                                   + e.getEventName() + " con tickets vendidos.");
                    return false;
                }
                e.setVenue(null);
            }
        }
        venues.remove(venueToRemove);
        autosave();
        return true;
    }

    /**
    * Removes an event by its ID after validating several conditions.
    * The event can only be removed if no tickets have already been sold for it.
    * If safe to remove, the event is deleted and an autosave is triggered.
    */
    public boolean removeEvent(int eventId) throws Exception{
        Event toRemove = null;

        for (Event ev : events) {
            if (ev.getEventId() == eventId) {
                toRemove = ev;
                break;
            }
        }

        if(toRemove != null){
            if(!toRemove.getTickets().isEmpty()){
                throw new IllegalStateException("Ya se han vendido tiquetes para este evento");
            }
            events.remove(toRemove);
            autosave(); // ⬅ autoguardado inmediato
            return true;
        }
        return false; // No encontrado
    }

    // Removes a customer from the customer list if their ID matches. Persists changes upon success.
    public boolean removeCustomer(int id) throws Exception{
        Customer target = null;
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

    /* 
    * Encodes a list of Event objects into a CSV format (using a custom CSVEncoder implementation)
    * and saves the resulting data to a file named "CSVEvents.csv". 
    */
    public void encoderEvents(List<Event> listEvents) throws IOException{
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

    /*
    * Encodes a list of Customer objects into a CSV format (using a custom CSVEncoder implementation)
    * and saves the resulting data to a file named "CSVCustomers.csv" 
    */
    public void encoderCustomer(List<Customer> listCustomers) throws IOException{
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
    /**
    * Encodes a list of Venue objects into a CSV format (using a custom CSVEncoder implementation)
    * and saves the resulting data to a file named "CSVVenues.csv".
    */
    public void encoderVenues(List<Venue> listVenues) throws IOException{
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
    /* 
    * Saves the provided CSV formatted content string to a specified file name using a FileWriter.
    * Uses try-with-resources to ensure the writer is closed automatically
    */
    public static void saveCSV(String fileName, String csvContent) throws IOException{
        try(FileWriter writer = new FileWriter(fileName)){
            writer.write(csvContent);
        }
    }

    // The following methods return the core attributes and data lists of the ticket office system
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
