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
        panel.setLayout(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 1. Comprar ticket
        JButton buyTicketBtn = new JButton("Comprar Ticket");
        buyTicketBtn.addActionListener(e -> {
        new EventSelectionUI(office).setVisible(true);
        dispose();
        });

        // 2. Cargar datos
        JButton loadBtn = new JButton("Cargar Datos");
        loadBtn.addActionListener(e -> {

            String basePath = System.getProperty("user.dir");
            String filePath = basePath + File.separator + "ticketoffice.dat";

            TicketOffice loaded = data.TicketOfficeStorage.load(filePath);

             if (loaded != null) {
                 this.office = loaded;
                 JOptionPane.showMessageDialog(this, "Datos cargados exitosamente.");

                new MainMenu(office).setVisible(true);
                 dispose();

            } else {
                JOptionPane.showMessageDialog(this, "No se encontró archivo o ocurrió un error.");
            }
        });

        // 3. Guardar datos
        JButton saveBtn = new JButton("Guardar Datos");
        saveBtn.addActionListener(e -> {

            String basePath = System.getProperty("user.dir");
            String filePath = basePath + File.separator + "ticketoffice.dat";

            data.TicketOfficeStorage.save(office, filePath);

            JOptionPane.showMessageDialog(this,
            "Datos guardados en:\n" + filePath);
        });

        // 4. Agregar eventos
        JButton addEventBtn = new JButton("Agregar Eventos");
        addEventBtn.addActionListener(e -> {
                new AddEventUI(office).setVisible(true);
                dispose();
        });

        // 5. Agregar clientes
        JButton addCustomerBtn = new JButton("Agregar Clientes");
        addCustomerBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Interfaz Agregar Clientes (por implementar)");
        });

        // 6. Consultar tickets de clientes
        JButton checkTicketsBtn = new JButton("Consultar Tickets de Clientes");
        checkTicketsBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Interfaz Consulta Tickets (por implementar)");
        });

        // Agregar botones al panel
        panel.add(buyTicketBtn);
        panel.add(loadBtn);
        panel.add(saveBtn);
        panel.add(addEventBtn);
        panel.add(addCustomerBtn);
        panel.add(checkTicketsBtn);

        add(panel);
    }
}
