package ui;

import domain.Customer;
import domain.TicketOffice;

import javax.swing.*;
import java.awt.*;

public class EditCustomerUI extends JFrame {

    private TicketOffice office;
    private Customer customer;

    public EditCustomerUI(TicketOffice office, Customer customer) {
        this.office = office;
        this.customer = customer;

        setTitle("Editar Cliente");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(6, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        form.add(new JLabel("ID (no editable):"));
        JTextField idField = new JTextField(String.valueOf(customer.getCustomerId()));
        idField.setEditable(false);
        form.add(idField);

        form.add(new JLabel("Nombre:"));
        JTextField nameField = new JTextField(customer.getCustomerName());
        form.add(nameField);

        form.add(new JLabel("Apellido:"));
        JTextField lastnameField = new JTextField(customer.getCustomerLastname());
        form.add(lastnameField);

        form.add(new JLabel("Dirección:"));
        JTextField addressField = new JTextField(customer.getCustomerAddress());
        form.add(addressField);

        form.add(new JLabel("Email:"));
        JTextField emailField = new JTextField(customer.getCustomerEmail());
        form.add(emailField);

        form.add(new JLabel("Teléfono:"));
        JTextField phoneField = new JTextField(customer.getCustomerPhoneNumber());
        form.add(phoneField);

        add(form, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 15, 10));

        JButton saveBtn = new JButton("Guardar");
        JButton backBtn = new JButton("Volver");

        btnPanel.add(saveBtn);
        btnPanel.add(backBtn);

        add(btnPanel, BorderLayout.SOUTH);

        saveBtn.addActionListener(e -> {
            if (nameField.getText().isBlank() || lastnameField.getText().isBlank()) {
                JOptionPane.showMessageDialog(this,
                        "Nombre y apellido no pueden estar vacíos.");
                return;
            }

            try{
                customer.setCustomerAddress(addressField.getText());
                customer.setCustomerEmail(emailField.getText());
                customer.setCustomerPhoneNumber(phoneField.getText());
                office.autosave();
                JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente.");   
            }catch (Exception ex){
                JOptionPane.showMessageDialog(this, "Error al guardar el archivo");
            }finally{
                dispose();
                new ManageCustomerUI(office).setVisible(true);
            }
        });

        backBtn.addActionListener(e -> {
            dispose();
            new ManageCustomerUI(office).setVisible(true);
        });

    }
}
