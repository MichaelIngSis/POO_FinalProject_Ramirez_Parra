package ui;

import domain.Event;
import domain.Location;
import domain.TicketOffice;

import javax.swing.*;
import java.awt.*;

public class ManageLocationsUI extends JFrame {

    private TicketOffice office;
    private DefaultListModel<Location> model = new DefaultListModel<>();
    private JList<Location> list;
    private JComboBox<Event> eventCombo;

    public ManageLocationsUI(TicketOffice office) {
        this.office = office;

        setTitle("Administrar Localidades por Evento");
        setSize(550, 430);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ---------- PANEL SUPERIOR ----------
        JPanel topPanel = new JPanel(new GridLayout(2, 1));

        eventCombo = new JComboBox<>();
        for (Event e : office.getEvents()) eventCombo.addItem(e);

        topPanel.add(eventCombo);

        JTextField searchField = new JTextField();
        JButton searchBtn = new JButton("Buscar");

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchBtn, BorderLayout.EAST);

        topPanel.add(searchPanel);

        add(topPanel, BorderLayout.NORTH);

        // ---------- LISTA ----------
        list = new JList<>(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        refreshList("");
        add(new JScrollPane(list), BorderLayout.CENTER);

        // ---------- BOTONES ----------
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

        // ---------- LISTENERS ----------
        eventCombo.addActionListener(e -> refreshList(""));

        searchBtn.addActionListener(e ->
                refreshList(searchField.getText()));

        // ---------- AGREGAR ----------
        addBtn.addActionListener(e -> {
            Event ev = (Event) eventCombo.getSelectedItem();
            if (ev == null) return;

            String name = JOptionPane.showInputDialog("Nombre de la localidad:");
            if (name == null || name.isBlank()) return;

            String capStr = JOptionPane.showInputDialog("Capacidad:");
            if (capStr == null) return;

            try {
                int cap = Integer.parseInt(capStr);
                office.addLocationToEvent(ev, name, cap);
                refreshList("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        // ---------- EDITAR ----------
        editBtn.addActionListener(e -> {
            Event ev = (Event) eventCombo.getSelectedItem();
            Location loc = list.getSelectedValue();

            if (ev == null || loc == null) {
                JOptionPane.showMessageDialog(this, "Seleccione una localidad.");
                return;
            }

            String newName = JOptionPane.showInputDialog("Nuevo nombre:", loc.getLocationName());
            if (newName == null || newName.isBlank()) return;

            String capStr = JOptionPane.showInputDialog("Nueva capacidad:", loc.getLocationCapacity());
            if (capStr == null) return;

            try {
                int newCap = Integer.parseInt(capStr);
                office.updateLocationOfEvent(ev, loc, newName, newCap);
                refreshList("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        delBtn.addActionListener(e -> {
            Event ev = (Event) eventCombo.getSelectedItem();
            Location loc = list.getSelectedValue();

            if (ev == null || loc == null) {
                JOptionPane.showMessageDialog(this, "Seleccione una localidad.");
                return;
            }

            int ok = JOptionPane.showConfirmDialog(this,
                    "¿Eliminar la localidad " + loc.getLocationName() + "?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION);

            if (ok == JOptionPane.YES_OPTION) {
                try{
                    office.removeLocationFromEvent(ev, loc);
                    refreshList("");
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar la localidad, ya que hay tiquetes vendidos");
                }
                
            }
        });

        // ---------- VOLVER ----------
        backBtn.addActionListener(e -> {
            new MainMenu(office).setVisible(true);
            dispose();
        });
    }

    private void refreshList(String filter) {
        model.clear();
        Event ev = (Event) eventCombo.getSelectedItem();
        if (ev == null) return;

        for (Location loc : ev.getLocations()) {
            if (filter.isEmpty() ||
                loc.getLocationName().toLowerCase().contains(filter.toLowerCase())) {
                model.addElement(loc);
            }
        }
    }
}
