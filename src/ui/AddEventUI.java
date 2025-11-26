package ui;

import domain.TicketOffice;
import domain.Venue;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AddEventUI extends JFrame {

    public AddEventUI(TicketOffice office) {

        setTitle("Agregar Evento");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2));

        JTextField nameField = new JTextField();
        JTextField dateField = new JTextField("2025-01-01");   // String
        JTextField timeField = new JTextField("1900");         // int HHmm

        String[] types = {"Concierto", "Teatro", "Deporte", "Cine", "Otro"};
        JComboBox<String> typeCombo = new JComboBox<>(types);

        List<Venue> venues = office.getVenues();  // Debes tener este método
        JComboBox<Venue> venueCombo = new JComboBox<>(venues.toArray(new Venue[0]));

        // Para que se muestre el nombre del venue
        venueCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value instanceof Venue) {
                    Venue v = (Venue) value;
                    setText(v.getVenueId() + " - " + v.getVenueName());
                }
                return this;
            }
        });

        JButton addBtn = new JButton("Agregar");
        JButton backBtn = new JButton("Volver");

        add(new JLabel("Nombre del Evento:"));
        add(nameField);

        add(new JLabel("Fecha (YYYY-MM-DD):"));
        add(dateField);

        add(new JLabel("Hora (HHmm):"));
        add(timeField);

        add(new JLabel("Tipo de Evento:"));
        add(typeCombo);

        add(new JLabel("Lugar (Venue):"));
        add(venueCombo);

        add(backBtn);
        add(addBtn);

        addBtn.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String date = dateField.getText();

                int time = Integer.parseInt(timeField.getText());
                if (time < 0 || time >= 2400){
                    throw new IllegalArgumentException("La hora debe estar entre 0000 y 2359");
                }
                String type = (String) typeCombo.getSelectedItem();
                Venue selectedVenue = (Venue) venueCombo.getSelectedItem();
                if (selectedVenue == null){
                    throw new IllegalArgumentException("Debe seleccionar un venue.");
                }
                office.addEvent(name,date,time,type,selectedVenue);
                JOptionPane.showMessageDialog(this, "Evento agregado correctamente");
                new ManageEventsUI(office).setVisible(true);
                dispose();
                
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            } catch (Exception ex){
                JOptionPane.showMessageDialog(this, "Error al guardar el archivo");
            }
        });

        backBtn.addActionListener(e -> {
            dispose();
            new ManageEventsUI(office).setVisible(true);
        });
    }
}
