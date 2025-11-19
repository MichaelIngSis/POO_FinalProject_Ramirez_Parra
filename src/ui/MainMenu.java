package ui;
import domain.TicketOffice;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainMenu extends JFrame {

    private TicketOffice office;

    public MainMenu(TicketOffice office) {
        
        this.office = office;

        setTitle("Ticket Office");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar ventana

        // Panel vertical para botones
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 1. Comprar ticket
        JButton buyTicketBtn = new JButton("Comprar Ticket");
        buyTicketBtn.addActionListener(e -> {
        new EventSelectionUI(office).setVisible(true);
        dispose();
        });

        // 6. Consultar tickets de clientes
        JButton checkTicketsBtn = new JButton("Consultar Tickets de Clientes");
        checkTicketsBtn.addActionListener(e -> {
            new CheckCustomerTicketsUI(office).setVisible(true);
            dispose();
        });

        JButton manageVenuesBtn = new JButton("Administrar Venues");
        manageVenuesBtn.addActionListener(e -> {
            dispose();
            new ManageVenuesUI(office).setVisible(true);
        });

        JButton manageEventsBtn = new JButton("Administrar Eventos");
        manageEventsBtn.addActionListener(e -> {
            new ManageEventsUI(office).setVisible(true);
            dispose();
        });

        JButton manageCustomerBtn = new JButton("Administrar Clientes");
        manageCustomerBtn.addActionListener(e -> {
            new ManageCustomerUI(office).setVisible(true);
            dispose();
        });

        JButton manageLocationBtn = new JButton("Administrar Localidades");
        manageLocationBtn.addActionListener(e -> {
            new ManageLocationsUI(office).setVisible(true);
            dispose();
        });

        // Agregar botones al panel
        panel.add(buyTicketBtn);
        panel.add(checkTicketsBtn);
        panel.add(manageVenuesBtn);
        panel.add(manageEventsBtn);
        panel.add(manageCustomerBtn);
        panel.add(manageLocationBtn);


        add(panel);
    }
}
