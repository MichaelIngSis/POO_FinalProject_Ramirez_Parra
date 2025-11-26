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
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Seleccione un evento:", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        DefaultListModel<String> model = new DefaultListModel<>();
        for (Event e : office.getEvents()) {
            model.addElement(e.getEventName());
        }

        JList<String> eventList = new JList<>(model);
        add(new JScrollPane(eventList), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton continueBtn = new JButton("Continuar");
        JButton backBtn = new JButton("Volver");

        bottomPanel.add(backBtn);
        bottomPanel.add(continueBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        continueBtn.addActionListener(e -> {
            int index = eventList.getSelectedIndex();
            if (index == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un evento");
                return;
            }

            Event event = office.getEvents().get(index);
            if(event.getLocations().isEmpty()){
                JOptionPane.showMessageDialog(this,"No hay localidades asignadas a este evento");
            }else{
                new TicketPurchaseUI(office, event).setVisible(true);
                dispose();
            }
        });

        backBtn.addActionListener(e -> {
            new MainMenu(office).setVisible(true);
            dispose();
        });
    }
}

