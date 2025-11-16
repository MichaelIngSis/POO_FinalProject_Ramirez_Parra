package domain;
public class Location {
    private String locationName;
    private int locationCapacity; //Hacer una agregación de Location en Event
    private int availableSeats;
    private int nextSeatNumber;

    public Location(String locationName, int locationCapacity){
        this.locationName = locationName;
        this.locationCapacity = locationCapacity;
        this.availableSeats = locationCapacity;
        this.nextSeatNumber = 1;
    }

    public boolean hasAvailability(){
        return availableSeats > 0;
    }


    public int assignSeat(){
        if(!hasAvailability()){
            throw new IllegalStateException("No seats available in location: " + locationName);
        }
        int assignedSeat = nextSeatNumber;
        nextSeatNumber++;
        availableSeats--;

        return assignedSeat;
    }

    public void releaseSeat(){
        if(availableSeats < locationCapacity){
            availableSeats ++;
        }
    }

    public void setLocationName(String locationName){
        if(!locationName.equals("")){
            this.locationName = locationName;
        }
    }

    public void setLocationCapacity(int locationCapacity){
        this.locationCapacity = locationCapacity;
    }

    public String getLocationName(){return locationName;}
    public int getLocationCapacity(){return locationCapacity;}
    public int getAvailableSeats(){return availableSeats;}
}
