package ui;

import domain.Event;
import domain.Location;
import domain.Customer;

import javax.swing.*;
import java.awt.*;

public class TicketConfirmationUI extends JFrame {

    public TicketConfirmationUI(Event event, Location loc, Customer customer) {

        setTitle("Ticket Confirmado");
        setSize(350, 200);
        setLocationRelativeTo(null);

        int lastSeat = loc.getLocationCapacity() - loc.getAvailableSeats() + 1;

        JLabel info = new JLabel("<html>" +
            "<h2>¡Ticket Comprado!</h2>" +
            "Evento: " + event.getEventName() + "<br>" +
            "Location: " + loc.getLocationName() + "<br>" +
            "Asiento: " + lastSeat + "<br>" +
            "Cliente: " + customer.getCustomerName() +
            "</html>");

        add(info, BorderLayout.CENTER);
    }
}