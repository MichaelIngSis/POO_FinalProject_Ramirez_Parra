package domain;
/*This class is create for input initial data to the system and 
  is used to implement tests in the application */
public class DemoData {

    //Initializes application starup configuration data
    public static TicketOffice getTicketOfficeWithSampleData() {

        //Create a TicketOffice object.
        TicketOffice office = new TicketOffice(
                1001,
                "Calle 89",
                "oficina@example.com",
                "+57 320302",
                "Manizales"
        );

        //Add venues to the office instance. 
        try{
          office.addVenue("Aula Máxima", "Carrera 20", 3000, "Eventos de Música");
          office.addVenue("Estadio Palogrande", "La Estrella", 30000, "Eventos Deportivos");
        }catch(Exception e){

        }



        //Add events to the office instance.
        try{
          office.addEvent("Perros Criollos", "26/10/2026", 1900, "Concierto",office.getVenues().get(0));
          office.addEvent("Perras Criollas", "27/10/2028", 1900, "Concierto",office.getVenues().get(0));
        }catch(Exception e){

        }
        

        //Create location for first event.
        office.getEvents().get(0).createLocations("Palcos", 1000);
        office.getEvents().get(0).createLocations("General", 2000);
        
        //Create location for second event
        office.getEvents().get(1).createLocations("Palcos", 1000);
        office.getEvents().get(1).createLocations("General", 2000);

        //Create a test customer
        Customer customer = new Customer(
                1001,
                "Michael",
                "Ramirez",
                "Calle 76",
                "michael@example.com",
                "+57 91921"
        );

        //Append the test customer to the customer list.
        try{
          office.addCustomer(customer);
        }catch(Exception e){
          
        }
        
        return office;
    }
}