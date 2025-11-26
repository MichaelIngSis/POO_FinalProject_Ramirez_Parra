package data;
import domain.TicketOffice;
import java.io.*;
public class TicketOfficeStorage {
    
    public static void save(TicketOffice ticketOffice, String filename) throws IOException {
        // Eliminamos el try-catch de aquí y añadimos "throws IOException" a la firma
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))){
            out.writeObject(ticketOffice);
        }
    }

    public static TicketOffice load(String filename){
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))){
            return (TicketOffice) in.readObject();
        }catch(IOException | ClassNotFoundException e){
            System.err.println("Error loading enterprise data: "+ e.getMessage());
            return null;
        }
    }
}
