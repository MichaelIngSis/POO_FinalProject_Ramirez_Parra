package domain;

public class DemoData {

    public static TicketOffice getTicketOfficeWithSampleData() {

        TicketOffice office = new TicketOffice(
                1001,
                "Calle 89",
                "oficina@example.com",
                "+57 320302",
                "Manizales"
        );

        // Crear eventos
        Event event1 = new Event(1,"Perros Criollos", "26/10/2026", 1900, "Música");
        Event event2 = new Event(2,"Perras Criollas", "27/10/2028", 1900, "Música");

        
        office.addVenue("Aula Máxima", "Carrera 20", 3000, "Eventos de Música");
        office.addVenue("Estadio Palogrande", "La Estrella", 30000, "Eventos Deportivos");
 
        event1.setVenue(office.getVenues().get(0));
        event2.setVenue(office.getVenues().get(0));

        // Locations
        Location location1 = new Location("Palcos", 1000);
        event1.createLocations("Palcos", 1000);
        event1.createLocations("General", 2000);

        event2.createLocations("Palcos", 1000);
        event2.createLocations("General", 2000);

        // Registrar eventos en la oficina
        office.addEvent(event1);
        office.addEvent(event2);

        // Cliente ficticio
        Customer customer = new Customer(
                1001,
                "Michael",
                "Ramirez",
                "Calle 76",
                "michael@example.com",
                "+57 91921"
        );

        office.addCustomer(customer);

        return office;
    }
}