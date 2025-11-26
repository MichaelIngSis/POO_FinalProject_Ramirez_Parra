package ui;

import domain.Customer;
import domain.TicketOffice;

import javax.swing.*;
import java.awt.*;

public class ManageCustomerUI extends JFrame {

    private TicketOffice office;
    private DefaultListModel<Customer> model = new DefaultListModel<>();
    private JList<Customer> list;

    public ManageCustomerUI(TicketOffice office) {
        this.office = office;

        setTitle("Administrar Clientes");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new BorderLayout());
        JTextField searchField = new JTextField();
        JButton searchBtn = new JButton("Buscar");

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchBtn, BorderLayout.EAST);

        add(searchPanel, BorderLayout.NORTH);

        list = new JList<>(model);
        refreshList("");
        add(new JScrollPane(list), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new GridLayout(1, 4, 10, 10));

        JButton addBtn = new JButton("Agregar");
        JButton editBtn = new JButton("Modificar");
        JButton deleteBtn = new JButton("Eliminar");
        JButton backBtn = new JButton("Volver");

        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(backBtn);

        add(btnPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            new AddCustomerUI(office).setVisible(true);
            dispose();
        });


        searchBtn.addActionListener(e ->
                refreshList(searchField.getText())
        );

        deleteBtn.addActionListener(e -> {
            Customer c = list.getSelectedValue();
            if (c == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente.");
                return;
            }

            int opt = JOptionPane.showConfirmDialog(
                    this,
                    "¿Seguro que desea eliminar el cliente?\n" + c,
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION
            );

            if (opt == JOptionPane.YES_OPTION) {
                try{
                    office.removeCustomer(c.getCustomerId());
                    refreshList(searchField.getText());
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Ocurrio un error al guardar el archivo");
                }
            }
        });

        editBtn.addActionListener(e -> {
            Customer c = list.getSelectedValue();
            if (c == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente.");
                return;
            }

            new EditCustomerUI(office, c).setVisible(true);
            dispose();
        });

        backBtn.addActionListener(e -> {
            dispose();
            new MainMenu(office).setVisible(true);
        });

    }

    private void refreshList(String filter) {
        model.clear();
        filter = filter.toLowerCase();

        for (Customer c : office.getCustomers()) {
            String str = (c.getCustomerId() + " " + c.getCustomerName() + " " + c.getCustomerLastname()).toLowerCase();
            if (str.contains(filter)) {
                model.addElement(c);
            }
        }
    }
}
