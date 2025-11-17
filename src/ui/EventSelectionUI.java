package ui;

import domain.TicketOffice;
import domain.Event;

import javax.swing.*;
import java.awt.*;

public class EventSelectionUI extends JFrame {

    private TicketOffice office;

    public EventSelectionUI(TicketOffice office) {
        this.office = office;

        setTitle("Seleccionar Evento");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Seleccione un evento:", SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        // Lista de eventos
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Event e : office.getEvents()) {
            model.addElement(e.getEventName());
        }

        JList<String> eventList = new JList<>(model);
        add(new JScrollPane(eventList), BorderLayout.CENTER);

        JButton continueBtn = new JButton("Continuar");
        continueBtn.addActionListener(e -> {
            int index = eventList.getSelectedIndex();
            if (index == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un evento");
                return;
            }

            Event event = office.getEvents().get(index);
            new TicketPurchaseUI(office, event).setVisible(true);
            dispose();
        });

        add(continueBtn, BorderLayout.SOUTH);
    }
}

