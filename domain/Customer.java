import java.util.List;

public class Customer {
    private int customerId;
    private String customerName;
    private String customerLastname;
    private String customerAddress;
    private String customerEmail;
    private String customerPhoneNumber;
    private List<Ticket> tickets;

    public Customer(int customerId, String customerName, String customerLastname,
                    String customerAddress, String customerEmail, String customerPhoneNumber){
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerLastname = customerLastname;
        this.customerAddress = customerAddress;
        this.customerEmail = customerEmail;
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public void setCustomerAddress(String newCustomerAddress){
        if(!newCustomerAddress.equals("")){
            customerAddress = newCustomerAddress;
        }
    }

    public void setCustomerEmail(String newCustomerEmail){
        if(!newCustomerEmail.equals("")){
            customerEmail = newCustomerEmail;
        }
    }

    public void setCustomerPhoneNumber(String newCustomerPhoneNumber){
        if(!newCustomerPhoneNumber.equals("")){
            customerPhoneNumber = newCustomerPhoneNumber;
        }
    }

    public void saveTicket(Ticket ticket){
        tickets.add(ticket);
    }

    public int getCustomerId(){return customerId;}
    public String getCustomerName(){return customerName;}
    public String getCustomerLastname(){return customerLastname;}
    public String getCustomerAddress(){return customerAddress;}
    public String getCustomerEmail(){return customerEmail;}
    public String getCustomerPhoneNumber(){return customerPhoneNumber;}
}
