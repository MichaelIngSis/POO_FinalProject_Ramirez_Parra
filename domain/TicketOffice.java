import java.util.List;

public class TicketOffice {
    private int ticketOfficeNit;
    private String ticketOfficeAddress;
    private String ticketOfficeEmail;
    private String ticketOfficePhoneNumber;
    private String ticketOfficeCity;
    private List<Customer> customers;
    private List<Event> events;

    public TicketOffice(int ticketOfficeNit, String ticketOfficeAddress, String ticketOfficeEmail, String ticketOfficePhoneNumber,
            String ticketOfficeCity){
        this.ticketOfficeNit = ticketOfficeNit;
        this.ticketOfficeAddress = ticketOfficeAddress;
        this.ticketOfficeEmail = ticketOfficeEmail;
        this.ticketOfficePhoneNumber = ticketOfficePhoneNumber;
        this.ticketOfficeCity = ticketOfficeCity;
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

    public int getTicketOfficeNit(){return ticketOfficeNit;}
    public String getTicketOfficeAddress(){return ticketOfficeAddress;}
    public String getTicketOfficeEmail(){return ticketOfficeEmail;}
    public String getTicketOfficePhoneNumber(){return ticketOfficePhoneNumber;}
    public String getTicketOfficeCity(){return ticketOfficeCity;}
}
