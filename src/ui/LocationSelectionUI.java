package ui;

import domain.Event;
import domain.Location;
import domain.TicketOffice;
import domain.Customer;

import javax.swing.*;
import java.awt.*;

public class LocationSelectionUI extends JFrame {

    public LocationSelectionUI(Event event, TicketOffice office) {
        setTitle("Seleccionar Location");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JComboBox<String> locationCombo = new JComboBox<>();

        for (Location loc : event.getLocations()) {
            locationCombo.addItem(loc.getLocationName() + 
                " (Disp: " + loc.getAvailableSeats() + ")");
        }

        JButton buyBtn = new JButton("Comprar");

        buyBtn.addActionListener(e -> {

            int index = locationCombo.getSelectedIndex();
            Location selectedLoc = event.getLocations().get(index);

            // Simulación: usamos el primer cliente
            Customer c = office.getCustomers().get(0);

            office.sellTicket(event, c, selectedLoc);

            new TicketConfirmationUI(event, selectedLoc, c).setVisible(true);
            dispose();
        });

        setLayout(new BorderLayout());
        add(locationCombo, BorderLayout.CENTER);
        add(buyBtn, BorderLayout.SOUTH);
    }
}