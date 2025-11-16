package ui;

import domain.Event;
import domain.TicketOffice;
import domain.DemoData;

import javax.swing.*;
import java.awt.*;

public class EventSelectionUI extends JFrame {

    private JComboBox<String> eventCombo;
    private TicketOffice office;

    public EventSelectionUI() {

        // Aquí deberías pasar un TicketOffice ya configurado
        office = DemoData.getTicketOfficeWithSampleData();

        setTitle("Seleccionar Evento");
        setSize(400, 300);
        setLocationRelativeTo(null);

        eventCombo = new JComboBox<>();

        for (Event ev : office.getEvents()) {
            eventCombo.addItem(ev.getEventName());
        }

        JButton nextBtn = new JButton("Continuar");

        nextBtn.addActionListener(e -> {
            int index = eventCombo.getSelectedIndex();
            Event selected = office.getEvents().get(index);
            new LocationSelectionUI(selected, office).setVisible(true);
            dispose();
        });

        setLayout(new BorderLayout());
        add(eventCombo, BorderLayout.CENTER);
        add(nextBtn, BorderLayout.SOUTH);
    }
}