import ui.MainMenu;
import domain.TicketOffice;

import java.io.File;

import domain.DemoData;

public class App {
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(() -> {

            String basePath = System.getProperty("user.dir");
            String filePath = basePath + File.separator + "ticketoffice.dat";

            TicketOffice office = data.TicketOfficeStorage.load(filePath);

            if (office == null) {
                office = DemoData.getTicketOfficeWithSampleData();
                try{
                    data.TicketOfficeStorage.save(office, filePath); //  Guardar primera vez
                }catch(Exception e){
                    
                }
            }

            new MainMenu(office).setVisible(true);
        });
    }
}