package ui;

import domain.Event;
import domain.TicketOffice;
import domain.Venue;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import java.awt.*;

public class EditEventUI extends JFrame {

    private TicketOffice office;
    private Event event;

    public EditEventUI(TicketOffice office, Event event) {
        this.office = office;
        this.event = event;

        setTitle("Editar Evento - ID: " + event.getEventId());
        setSize(400, 350);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2, 10, 10));

        JTextField nameField = new JTextField(event.getEventName());
        JTextField dateField = new JTextField(event.getEventDate());
        MaskFormatter mf = null;
        try{
            mf = new MaskFormatter("##:##");
            mf.setPlaceholderCharacter('0');
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Ha ocurrido un error con el formato de la hora");
        }
        JFormattedTextField timeField = new JFormattedTextField(mf);
        timeField.setText(String.valueOf(event.getEventTime()));

        String[] types = {"Concierto", "Teatro", "Deporte", "Cine", "Otro"};
        JComboBox<String> typeCombo = new JComboBox<>(types);
        typeCombo.setSelectedItem(event.getEventType());


        JComboBox<Venue> venueCombo = new JComboBox<>();
        for (Venue v : office.getVenues()) {
            venueCombo.addItem(v);
        }
        venueCombo.setSelectedItem(event.getVenue());

        JButton saveBtn = new JButton("Guardar Cambios");
        JButton backBtn = new JButton("Volver");

        add(new JLabel("Nombre:"));
        add(nameField);

        add(new JLabel("Fecha (YYYY-MM-DD):"));
        add(dateField);

        add(new JLabel("Hora (HHmm):"));
        add(timeField);

        add(new JLabel("Tipo:"));
        add(typeCombo);

        add(new JLabel("Venue:"));
        add(venueCombo);

        add(saveBtn);
        add(backBtn);

        saveBtn.addActionListener(e -> {

            try {
                int time = Integer.parseInt(timeField.getText().replace(":",""));
                if (time < 0 || time >= 2400) {
                    throw new IllegalArgumentException("La hora debe estar entre 0000 y 2359");
                }
                event.setEventName(nameField.getText());
                event.setEventDate(dateField.getText());
                event.setEventTime(time);
                event.setEventType((String) typeCombo.getSelectedItem());
                event.setVenue((Venue) venueCombo.getSelectedItem());
                office.autosave();
                JOptionPane.showMessageDialog(this, "Evento actualizado correctamente.");
                dispose();
                new ManageEventsUI(office).setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        backBtn.addActionListener(e -> {
            dispose();
            new ManageEventsUI(office).setVisible(true);
        });
    }
}

