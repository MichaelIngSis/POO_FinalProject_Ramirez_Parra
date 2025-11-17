package ui;

import domain.Event;
import domain.TicketOffice;

import javax.swing.*;
import java.awt.*;

public class AddEventUI extends JFrame {

    public AddEventUI(TicketOffice office) {

        setTitle("Agregar Evento");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2));

        JTextField nameField = new JTextField();
        JTextField dateField = new JTextField("2025-01-01");   // String
        JTextField timeField = new JTextField("1900");         // int HHmm

        String[] types = {"Concierto", "Teatro", "Deporte", "Cine", "Otro"};
        JComboBox<String> typeCombo = new JComboBox<>(types);

        JButton addBtn = new JButton("Agregar");

        add(new JLabel("Nombre del Evento:"));
        add(nameField);

        add(new JLabel("Fecha (YYYY-MM-DD):"));
        add(dateField);

        add(new JLabel("Hora (HHmm):"));
        add(timeField);

        add(new JLabel("Tipo de Evento:"));
        add(typeCombo);

        add(new JLabel());
        add(addBtn);

        addBtn.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String date = dateField.getText();

                int time = Integer.parseInt(timeField.getText());
                if (time < 0 || time >= 2400)
                    throw new IllegalArgumentException("La hora debe estar entre 0000 y 2359");

                String type = (String) typeCombo.getSelectedItem();

                // ⬅ Crear el objeto EXACTO que tu clase requiere
                Event ev = new Event(name, date, time, type);

                office.addEvent(ev);

                JOptionPane.showMessageDialog(this, 
                    "Evento agregado con ID: " + ev.getEventId());
                
                dispose();
                new MainMenu(office).setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        setVisible(true);
    }
}
