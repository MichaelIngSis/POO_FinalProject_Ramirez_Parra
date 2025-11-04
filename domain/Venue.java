public class Venue {
    private int venueId;
    private String venueName;
    private String venueAddress;
    private int venueCapacity;
    private String venueCharacteristic;

    public Venue(int venueId, String venueName, String venueAddress, int venueCapacity, 
            String venueCharacteristic){
        this.venueId = venueId;
        this.venueName = venueName;
        this.venueAddress = venueAddress;
        this.venueCapacity = venueCapacity;
        if(venueCharacteristic.equals("Eventos de Música") || venueCharacteristic.equals("Eventos de Vehiculos") 
        || venueCharacteristic.equals("Eventos Deportivos") || venueCharacteristic.equals("Multiples Eventos")){
            this.venueCharacteristic = venueCharacteristic;
        }else{
            this.venueCharacteristic = "No especificada";
        }
    }

    public void setVenueName(String newVenueName){
        if(!newVenueName.equals("")){
            venueName = newVenueName;
        }
    }

    public void setVenueCharacteristic(String newVenueCharacteristic){
        if(newVenueCharacteristic.equals("Eventos de Música") || newVenueCharacteristic.equals("Eventos de Vehiculos") 
        || newVenueCharacteristic.equals("Eventos Deportivos") || newVenueCharacteristic.equals("Multiples Eventos")){
            venueCharacteristic = newVenueCharacteristic;
        }
    }

    public int getVenueId(){return venueId;}
    public String getVenueName(){return venueName;}
    public String getVenueAddress(){return venueAddress;}
    public int getVenueCapacity(){return venueCapacity;}
    public String getVenueCharacteristic(){return venueCharacteristic;}
}
