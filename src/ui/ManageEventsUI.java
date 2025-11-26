package ui;

import domain.Event;
import domain.TicketOffice;

import javax.swing.*;
import java.awt.*;

public class ManageEventsUI extends JFrame {

    private TicketOffice office;
    private DefaultListModel<Event> model = new DefaultListModel<>();
    private JList<Event> list;

    public ManageEventsUI(TicketOffice office) {
        this.office = office;

        setTitle("Administrar Eventos");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ---- Panel de búsqueda ----
        JPanel searchPanel = new JPanel(new BorderLayout());
        JTextField searchField = new JTextField();
        JButton searchBtn = new JButton("Buscar");
        
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchBtn, BorderLayout.EAST);

        add(searchPanel, BorderLayout.NORTH);

        // ---- Lista de eventos ----
        list = new JList<>(model);
        refreshList("");
        add(new JScrollPane(list), BorderLayout.CENTER);

        // ---- Panel inferior (botones CRUD) ----
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
            dispose();
            new AddEventUI(office).setVisible(true);
        });


        // ---- Acción Busqueda ----
        searchBtn.addActionListener(e ->
                refreshList(searchField.getText())
        );

        // ---- Acción Eliminar ----
        deleteBtn.addActionListener(e -> {
            Event ev = list.getSelectedValue();
            if (ev == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un evento.");
                return;
            }

            int opt = JOptionPane.showConfirmDialog(
                    this,
                    "¿Seguro que desea eliminar el evento?\n" + ev,
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION
            );

            if (opt == JOptionPane.YES_OPTION) {
                try{
                office.removeEvent(ev.getEventId());
                office.autosave();
                refreshList(searchField.getText());
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "No se puede eliminar porque ya se han vendido tiquetes para este evento.");
                }
            }
        });

        // ---- Acción Editar ----
        editBtn.addActionListener(e -> {
            Event ev = list.getSelectedValue();
            if (ev == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un evento.");
                return;
            }
            dispose();
            new EditEventUI(office, ev).setVisible(true);
        });

        // ---- Acción Volver ----
        backBtn.addActionListener(e -> {
            dispose();
            new MainMenu(office).setVisible(true);
        });
    }

    // --------------------------
    // Refrescar lista con filtro
    // --------------------------
    private void refreshList(String filter) {
        model.clear();

        for (Event ev : office.getEvents()) {
            if (filter == null || filter.isBlank()) {
                model.addElement(ev);
            } else {
                String f = filter.toLowerCase();
                if (ev.getEventName().toLowerCase().contains(f) ||
                    ev.getEventType().toLowerCase().contains(f)) {
                    model.addElement(ev);
                }
            }
        }
    }
}

