package ru.tyaguschev.view;

import ru.tyaguschev.services.DatabaseService;
import ru.tyaguschev.services.EmployeeService;
import ru.tyaguschev.services.PositionService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Mainframe extends JFrame {
    private DatabaseService currentService = new EmployeeService();
    private final JTable table = new JTable(currentService.getTableModel());

    public Mainframe() {
        getMenu();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void getMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu tableMenu = new JMenu("Tables");
        JMenuItem employeesTable = new JMenuItem("Employees");
        JMenuItem positionsTable = new JMenuItem("Positions");
        tableMenu.add(employeesTable);
        tableMenu.add(positionsTable);

        JMenu actionsMenu = new JMenu("Actions");
        JMenuItem insert = new JMenuItem("Insert");
        JMenuItem update = new JMenuItem("Update");
        JMenuItem delete = new JMenuItem("Delete");
        actionsMenu.add(insert);
        actionsMenu.add(update);
        actionsMenu.add(delete);

        menuBar.add(tableMenu);
        menuBar.add(actionsMenu);

        employeesTable.addActionListener(_ -> {
                currentService = new EmployeeService();
                currentService.updateTable();
                table.setModel(currentService.getTableModel());
        });
        positionsTable.addActionListener(_ -> {
                currentService = new PositionService();
                currentService.updateTable();
                table.setModel(currentService.getTableModel());
        });

        insert.addActionListener(_ -> {
                currentService.openInsertDialog();
        });

        update.addActionListener(_ -> {
            if (table.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(getParent(), "No row selected.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            currentService.openUpdateDialog(collectRowData());
        });

        delete.addActionListener((ActionEvent _) -> {
            if (table.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(getParent(), "No row selected.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
            currentService.delete(id);
        });

        setJMenuBar(menuBar);
    }

    private Object[] collectRowData() {
        int selectedRow = table.getSelectedRow();
        Object[] rowData = new Object[table.getColumnCount()];

        for (int i = 0; i < table.getColumnCount(); i++) {
            rowData[i] = table.getValueAt(selectedRow, i);
        }

        return rowData;
    }
}
