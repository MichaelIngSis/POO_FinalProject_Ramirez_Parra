package ui;

import domain.TicketOffice;
import domain.Event;
import domain.Customer;
import domain.Location;

import javax.swing.*;
import java.awt.*;

public class TicketPurchaseUI extends JFrame {

    private TicketOffice office;
    private Event event;

    public TicketPurchaseUI(TicketOffice office, Event event) {
        this.office = office;
        this.event = event;

        setTitle("Comprar Tickets - " + event.getEventName());
        setSize(450, 350);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 1, 10, 10));

        // --- Info del evento ---
        JLabel eventInfo = new JLabel(
                "Evento: " + event.getEventName(),
                SwingConstants.CENTER
        );

        // --- Selección de cliente ---
        JComboBox<String> clientBox = new JComboBox<>();
        for (Customer c : office.getCustomers()) {
            clientBox.addItem(c.getCustomerName());
        }

        if (office.getCustomers().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay clientes registrados.\nDebe agregar clientes primero.");
            dispose();
            return;
        }

        // --- Selección de Localidad ---
        JComboBox<String> locationBox = new JComboBox<>();
        for (Location loc : event.getLocations()) {
            locationBox.addItem(loc.getLocationName() + " (Disp: " + loc.getAvailableSeats() + ")");
        }

        if (event.getLocations().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Este evento no tiene localidades asignadas.");
            dispose();
            return;
        }

        // --- Cantidad de tickets ---
        SpinnerNumberModel qtyModel =
                new SpinnerNumberModel(1, 1, 100, 1);
        JSpinner qtySpinner = new JSpinner(qtyModel);

        JButton confirmBtn = new JButton("Confirmar Compra");

        // --- Acción de comprar ---
        confirmBtn.addActionListener(e -> {
            int clientIndex = clientBox.getSelectedIndex();
            int locIndex = locationBox.getSelectedIndex();
            int qty = (int) qtySpinner.getValue();

            if (clientIndex == -1) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente.");
                return;
            }

            if (locIndex == -1) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una localidad.");
                return;
            }

            Customer buyer = office.getCustomers().get(clientIndex);
            Location location = event.getLocations().get(locIndex);

            // verificar disponibilidad
            if (qty > location.getAvailableSeats()) {
                JOptionPane.showMessageDialog(this,
                        "No hay suficientes asientos disponibles en esta localidad.");
                return;
            }

            // realizar compra
            boolean success = office.sellTickets(event, location, buyer, qty);

            if (success) {
                JOptionPane.showMessageDialog(this,
                    "Compra realizada con éxito.\n" +
                    "Cliente: " + buyer.getCustomerName() + "\n" +
                    "Localidad: " + location.getLocationName() + "\n" +
                    "Tickets: " + qty);

                new MainMenu(office).setVisible(true);  // ← REGRESAR AL MENÚ
                dispose(); // cerrar ventana actual

            } else {
                JOptionPane.showMessageDialog(this,
                "Error: la compra no pudo realizarse.");
            }
        });

        add(eventInfo);
        add(new JLabel("Seleccione Cliente:", SwingConstants.CENTER));
        add(clientBox);
        add(new JLabel("Seleccione Localidad:", SwingConstants.CENTER));
        add(locationBox);
        add(new JLabel("Cantidad de Tickets:", SwingConstants.CENTER));
        add(qtySpinner);
        add(confirmBtn);
    }
}
