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
        Event event1 = new Event("Perros Criollos", "26/10/2026", 1900, "Música");
        Event event2 = new Event("Perras Criollas", "27/10/2028", 1900, "Música");

        // Crear venue
        Venue venue = new Venue("Aula Máxima", "Carrera 20", 3000, "Eventos de Música");

        event1.setVenue(venue);
        event2.setVenue(venue);

        // Locations
        event1.createLocations("Palcos", 1000);
        event1.createLocations("General", 2000);

        event2.createLocations("Palcos", 1000);
        event2.createLocations("General", 2000);

        // Registrar eventos en la oficina
        office.addEvents(event1);
        office.addEvents(event2);

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