package ui;

import domain.TicketOffice;
import domain.Venue;

import javax.swing.*;
import java.awt.*;

public class ManageVenuesUI extends JFrame {

    private TicketOffice office;
    private DefaultListModel<Venue> model = new DefaultListModel<>();
    private JList<Venue> list;

    public ManageVenuesUI(TicketOffice office) {
        this.office = office;

        setTitle("Administrar Venues");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ------- PANEL SUPERIOR: BÚSQUEDA -------
        JPanel searchPanel = new JPanel(new BorderLayout());
        JTextField searchField = new JTextField();
        JButton searchBtn = new JButton("Buscar");
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchBtn, BorderLayout.EAST);

        add(searchPanel, BorderLayout.NORTH);

        // ------- LISTA CENTRO -------
        list = new JList<>(model);
        refreshList("");   // ← carga todo
        add(new JScrollPane(list), BorderLayout.CENTER);

        // ------- PANEL BOTONES -------
        JPanel btnPanel = new JPanel();
        JButton addBtn = new JButton("Agregar");
        JButton editBtn = new JButton("Editar");
        JButton delBtn = new JButton("Eliminar");
        JButton backBtn = new JButton("Volver");

        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(delBtn);
        btnPanel.add(backBtn);
        add(btnPanel, BorderLayout.SOUTH);

        // ======== Acción buscar ========
        searchBtn.addActionListener(e ->
                refreshList(searchField.getText()));

        // ======== Acción agregar ========
        addBtn.addActionListener(e -> {
            dispose();
            new AddVenueUI(office).setVisible(true);   // UI para agregar
        });

        // ======== Acción editar ========
        editBtn.addActionListener(e -> {
            Venue v = list.getSelectedValue();
            if (v == null) {
                JOptionPane.showMessageDialog(this, "Selecciona un venue.");
                return;
            }
            dispose();
            new EditVenueUI(office, v).setVisible(true);
        });

        // ======== Acción borrar ========
        delBtn.addActionListener(e -> {
            Venue v = list.getSelectedValue();
            if (v == null) {
                JOptionPane.showMessageDialog(this, "Selecciona un venue.");
                return;
            }
            try{
                office.removeVenue(v.getVenueId());
                refreshList("");
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this,"Error al guardar el archivo");
            }
        });

        // ======== Acción volver ========
        backBtn.addActionListener(e -> {
            dispose();
            new MainMenu(office).setVisible(true);
        });
    }

    private void refreshList(String filter) {
        model.clear();
        for (Venue v : office.getVenues()) {
            if (filter.isEmpty() ||
                v.getVenueName().toLowerCase().contains(filter.toLowerCase())) {
                model.addElement(v);
            }
        }
    }
}
