package ui;

import domain.TicketOffice;
import domain.Customer;
import domain.Ticket;

import javax.swing.*;
import java.awt.*;

public class CheckCustomerTicketsUI extends JFrame {

    private TicketOffice office;

    public CheckCustomerTicketsUI(TicketOffice office) {
        this.office = office;

        setTitle("Consultar Tickets de Clientes");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        DefaultListModel<Customer> model = new DefaultListModel<>();
        for (Customer c : office.getCustomers()) {
            model.addElement(c);
        }

        JList<Customer> customerList = new JList<>(model);
        JScrollPane scroll = new JScrollPane(customerList);

        JTextArea ticketArea = new JTextArea();
        ticketArea.setEditable(false);
        JScrollPane ticketScroll = new JScrollPane(ticketArea);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scroll, ticketScroll);
        split.setDividerLocation(150);
        add(split, BorderLayout.CENTER);

        customerList.addListSelectionListener(e -> {
            Customer selected = customerList.getSelectedValue();
            if (selected != null) {
                ticketArea.setText(buildTicketInfo(selected));
            }
        });

        JButton backBtn = new JButton("Volver");
        backBtn.addActionListener(e -> {
            new MainMenu(office).setVisible(true);
            dispose();
        });

        add(backBtn, BorderLayout.SOUTH);

        setVisible(true);
    }

    private String buildTicketInfo(Customer c) {
        StringBuilder sb = new StringBuilder();

        sb.append("Tickets de: ").append(c.getCustomerName())
        .append(" ").append(c.getCustomerLastname()).append("\n\n");

        if (c.getTickets().isEmpty()) {
            sb.append("Este cliente no tiene tickets.");
            return sb.toString();
        }

        for (Ticket t : c.getTickets()) {
            sb.append("Evento: ").append(t.getEvent().getEventName()).append("\n")
                .append("Fecha: ").append(t.getEvent().getEventDate()).append("\n")
                .append("Localidad: ").append(t.getLocation().getLocationName()).append("\n")
                .append("Asiento: ").append(t.getTicketSeat()).append("\n")
                .append("Número de Tiquete: ").append(t.getTicketNumber()).append("\n")
                .append("--------------\n");
        }
        return sb.toString();
    }
}
