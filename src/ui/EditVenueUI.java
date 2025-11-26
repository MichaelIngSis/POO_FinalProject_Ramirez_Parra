package ui;

import domain.TicketOffice;
import domain.Venue;

import javax.swing.*;
import java.awt.*;

public class EditVenueUI extends JFrame{

    public EditVenueUI(TicketOffice office, Venue venue) {
        setTitle("Editar Venue");
        setSize(350, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2));

        JTextField name = new JTextField(venue.getVenueName());
        JTextField addr = new JTextField(venue.getVenueAddress());
        JTextField cap = new JTextField(String.valueOf(venue.getVenueCapacity()));
        JComboBox<String> type = new JComboBox<>(new String[]{
            "Eventos de Música",
            "Eventos de Vehiculos",
            "Eventos Deportivos",
            "Multiples Eventos"
        });
        type.setSelectedItem(venue.getVenueCharacteristic());

        JButton saveBtn = new JButton("Guardar");
        JButton backBtn = new JButton("Volver");

        add(new JLabel("Nombre:")); add(name);
        add(new JLabel("Dirección:")); add(addr);
        add(new JLabel("Capacidad:")); add(cap);
        add(new JLabel("Tipo:")); add(type);
        add(backBtn); add(saveBtn);

        saveBtn.addActionListener(e -> {
            try{
                venue.setVenueName(name.getText());
                venue.setVenueCharacteristic((String) type.getSelectedItem());
                venue.setVenueAddress(addr.getText());
                venue.setVenueCapacity(Integer.parseInt(cap.getText()));
                JOptionPane.showMessageDialog(this, "Se ha actualizado correctamente");
                office.autosave();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Error al guardar el archivo");
            }finally{
                dispose();
                new ManageVenuesUI(office).setVisible(true);
            }
        });

        backBtn.addActionListener(e -> {
            dispose();
            new ManageVenuesUI(office).setVisible(true);
        });
    }
}
