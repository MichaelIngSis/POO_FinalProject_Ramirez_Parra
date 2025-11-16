package ui;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame{

    public MainMenu() {
        setTitle("Ticket Office");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar ventana

        JButton buyTicketBtn = new JButton("Comprar Ticket");

        buyTicketBtn.addActionListener(e -> {
            new EventSelectionUI().setVisible(true);
            dispose();  // Cierra la ventana actual
        });

        add(buyTicketBtn, BorderLayout.CENTER);
    }
}
