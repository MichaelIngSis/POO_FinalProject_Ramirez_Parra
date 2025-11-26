package ui;

import domain.TicketOffice;

import javax.swing.*;
import java.awt.*;

public class AddVenueUI extends JFrame {

    public AddVenueUI(TicketOffice office) {

        setTitle("Agregar Venue");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2));

        JTextField nameField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField capacityField = new JTextField();

        // Características del venue
        String[] characteristics = {
                "Eventos de Música", 
                "Eventos de Vehiculos", 
                "Eventos Deportivos",
                "Multiples Eventos",
                "No especificada"
        };
        JComboBox<String> characteristicCombo = new JComboBox<>(characteristics);

        JButton addBtn = new JButton("Agregar");
        JButton backBtn = new JButton("Volver");

        add(new JLabel("Nombre del Venue:"));
        add(nameField);

        add(new JLabel("Dirección:"));
        add(addressField);

        add(new JLabel("Capacidad:"));
        add(capacityField);

        add(new JLabel("Característica:"));
        add(characteristicCombo);

        add(backBtn);
        add(addBtn);

        addBtn.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                String address = addressField.getText().trim();
                int capacity = Integer.parseInt(capacityField.getText());
                if (capacity <= 0){
                    throw new IllegalArgumentException("La capacidad debe ser mayor que cero.");
                }
                String characteristic = (String) characteristicCombo.getSelectedItem();
                office.addVenue(name, address, capacity, characteristic);
                JOptionPane.showMessageDialog(this, "Venue agregado correctamente");
                dispose();
                new ManageVenuesUI(office).setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        // ======== Acción volver ========
        backBtn.addActionListener(e -> {
            new ManageVenuesUI(office).setVisible(true);
            dispose();
        });
    }
}