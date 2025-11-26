package ui;

import domain.Customer;
import domain.TicketOffice;

import javax.swing.*;
import java.awt.*;

public class AddCustomerUI extends JFrame{
    
    public AddCustomerUI(TicketOffice office){
        setTitle("Agregar Cliente");
        setSize(400,300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7,2));

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField lastnameField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneNumberField = new JTextField();

        JButton addBtn = new JButton("Agregar");
        JButton backBtn = new JButton("Volver");

        add(new JLabel("Identificación del Cliente:"));
        add(idField);

        add(new JLabel("Nombre del Cliente:"));
        add(nameField);

        add(new JLabel("Apellido del Cliente:"));
        add(lastnameField);

        add(new JLabel("Dirección del Cliente:"));
        add(addressField);

        add(new JLabel("Correo Electronico del Cliente:"));
        add(emailField);

        add(new JLabel("Número de Teléfono del Cliente:"));
        add(phoneNumberField);

        add(backBtn);
        add(addBtn);

        addBtn.addActionListener(e -> {
            try{
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String lastname = lastnameField.getText();
                String address = addressField.getText();
                String email = emailField.getText();
                String phoneNumber = phoneNumberField.getText();

                Customer customer = new Customer(id, name, lastname, address, email, phoneNumber);
                office.addCustomer(customer);
                JOptionPane.showMessageDialog(this, "Cliente con id: " + customer.getCustomerId() + " agregado");
                dispose();
                new ManageCustomerUI(office).setVisible(true);
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Error: "+ ex.getMessage());
            }
        });

        backBtn.addActionListener(e -> {
            dispose();
            new ManageCustomerUI(office).setVisible(true);
        });

    }
}
