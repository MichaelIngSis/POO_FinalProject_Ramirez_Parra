package ui;

import javax.swing.*;

import domain.TicketOffice;

import java.awt.*;

public class ManageEncodeUI extends JFrame {
    private TicketOffice office;

    public ManageEncodeUI(TicketOffice office){
        this.office = office;

        setTitle("CSV Encoder");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10,10));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton encodeEventsBtn = new JButton("Exportar Eventos");
        encodeEventsBtn.addActionListener(e -> {
            try{
                office.encoderEvents(office.getEvents());
                JOptionPane.showMessageDialog(this, "Archivo guardado correctamente");
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Se ha presentado un error al guardar el archivo");
            }
        });

        JButton encodeCustomersBtn = new JButton("Exportar Clientes");
        encodeCustomersBtn.addActionListener(e -> {
            try{
                office.encoderCustomer(office.getCustomers());
                JOptionPane.showMessageDialog(this, "Archivo guardado correctamente");
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Se ha presentado un error al guardar el archivo");
            }
        });

        JButton encodeVenuesBtn = new JButton("Exportar Sitios de Eventos");
        encodeVenuesBtn.addActionListener(e -> {
            try{
                office.encoderVenues(office.getVenues());
                JOptionPane.showMessageDialog(this, "Archivo guardado correctamente");
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Se ha presentado un error al guardar el archivo");
            }
            
        });

        mainPanel.add(encodeEventsBtn);
        mainPanel.add(encodeCustomersBtn);
        mainPanel.add(encodeVenuesBtn);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JButton backBtn = new JButton("Volver");
        backBtn.setPreferredSize(new Dimension(100, 30));
        backBtn.addActionListener(e -> {
            new MainMenu(office).setVisible(true);
            dispose();
        });

        bottomPanel.add(backBtn);

        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}

