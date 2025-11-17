package ui;

import domain.Event;
import domain.TicketOffice;
import domain.Location;
import javax.swing.*;
import java.awt.*;

public class AddLocationUI extends JFrame {

    public AddLocationUI(TicketOffice office) {

        setTitle("Agregar Localidad a un Evento");
        setSize(450, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2));

        // 1. Seleccionar evento
        JComboBox<Event> eventCombo = new JComboBox<>(
                office.getEvents().toArray(new Event[0])
        );

        JTextField nameField = new JTextField();
        JTextField capacityField = new JTextField();

        JButton addBtn = new JButton("Agregar");

        add(new JLabel("Seleccionar Evento:"));
        add(eventCombo);

        add(new JLabel("Nombre de la Localidad:"));
        add(nameField);

        add(new JLabel("Capacidad:"));
        add(capacityField);

        add(new JLabel(""));
        add(addBtn);

        addBtn.addActionListener(e -> {
            try {
                Event event = (Event) eventCombo.getSelectedItem();
                if (event == null) throw new IllegalStateException("No hay eventos disponibles.");

                String locName = nameField.getText().trim();
                int locCapacity = Integer.parseInt(capacityField.getText());

                if (locCapacity <= 0)
                    throw new IllegalArgumentException("La capacidad debe ser mayor que cero.");

                // Crear localidad
                Location loc = new Location(locName, locCapacity);

                office.addLocationToEvent(event, loc);

                JOptionPane.showMessageDialog(this,
                        "Localidad agregada correctamente al evento: " + event.getEventName());

                dispose();
                new MainMenu(office).setVisible(true);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        setVisible(true);
    }
}
