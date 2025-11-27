package ui;

import domain.TicketOffice;
import domain.Event;
import domain.Customer;
import domain.Location;

import javax.swing.*;
import java.awt.*;

public class TicketPurchaseUI extends JFrame {

    private TicketOffice office;
    private Event event;

    public TicketPurchaseUI(TicketOffice office, Event event) {
        this.office = office;
        this.event = event;

        setTitle("Comprar Tickets - " + event.getEventName());
        setSize(450, 350);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(9, 1, 10, 10));

        JLabel eventInfo = new JLabel(
                "Evento: " + event.getEventName(),
                SwingConstants.CENTER
        );

        JComboBox<Customer> clientBox = new JComboBox<>();
        for (Customer c : office.getCustomers()) {
            clientBox.addItem(c);
        }

        if (office.getCustomers().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay clientes registrados.\nDebe agregar clientes primero.");
            dispose();
            return;
        }

        JComboBox<String> locationBox = new JComboBox<>();
        for (Location loc : event.getLocations()) {
            locationBox.addItem(loc.getLocationName() + " (Disp:" + loc.getAvailableSeats() + "): "+"$"+loc.getLocationPrice());
        }

        SpinnerNumberModel qtyModel =
                new SpinnerNumberModel(1, 1, 100, 1);
        JSpinner qtySpinner = new JSpinner(qtyModel);

        JButton confirmBtn = new JButton("Confirmar Compra");
        JButton backBtn = new JButton("Volver");

        confirmBtn.addActionListener(e -> {
            int clientIndex = clientBox.getSelectedIndex();
            int locIndex = locationBox.getSelectedIndex();
            int qty = (int) qtySpinner.getValue();

            if (clientIndex == -1) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente.");
                return;
            }

            if (locIndex == -1) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una localidad.");
                return;
            }

            Customer buyer = office.getCustomers().get(clientIndex);
            Location location = event.getLocations().get(locIndex);

            if (qty > location.getAvailableSeats()) {
                JOptionPane.showMessageDialog(this,"No hay suficientes asientos disponibles en esta localidad.");
                return;
            }

            int opt = JOptionPane.showConfirmDialog(
                    this,
                    "¿Seguro que desea realizar esta compra?\n"+
                    "Tickets: " + qty + "\n" +
                    "Precio ha pagar: " + qty*location.getLocationPrice(),
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION
            );

            if(opt == JOptionPane.YES_OPTION){
                try{
                    office.sellTickets(event, location, buyer, qty);
                    JOptionPane.showMessageDialog(this,
                        "Compra realizada con éxito.\n" +
                        "Cliente: " + buyer.getCustomerName() + "\n" +
                        "Localidad: " + location.getLocationName() + "\n" +
                        "Tickets: " + qty + "\n" +
                        "Precio Pagado: " + qty*location.getLocationPrice());
                    
                        dispose();
                        new MainMenu(office).setVisible(true);
                } catch(IllegalArgumentException | IllegalStateException ex){
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Se presenta un problema al guardar el archivo");
                }
            }
        });

        backBtn.addActionListener(e -> {
            new EventSelectionUI(office).setVisible(true);
            dispose();
        });

        add(eventInfo);
        add(new JLabel("Seleccione Cliente:", SwingConstants.CENTER));
        add(clientBox);
        add(new JLabel("Seleccione Localidad:", SwingConstants.CENTER));
        add(locationBox);
        add(new JLabel("Cantidad de Tickets:", SwingConstants.CENTER));
        add(qtySpinner);
        add(confirmBtn);
        add(backBtn);
    }
}
