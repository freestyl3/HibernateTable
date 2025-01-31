package ru.tyaguschev.view.Actions;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ActionDialog extends JDialog {
    protected ActionDialog(int WIDTH, int HEIGHT, String title) {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setTitle(title);
        setLocationRelativeTo(null);
    }

    protected void addRow(JPanel panel, GridBagConstraints gbc, int row, JLabel label, Component field) {
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(label, gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    protected void okAction() {}

    protected void addButtons(String okLabel) {
        JButton okButton = new JButton(okLabel);
        JButton cancelButton = new JButton("Cancel");
        // Панель для кнопок
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Обработчики событий
        okButton.addActionListener(e -> okAction());
        cancelButton.addActionListener(e -> dispose());
    }
}
