package ru.tyaguschev.view.Actions.Insert;

import ru.tyaguschev.models.Position;
import ru.tyaguschev.services.PositionService;
import ru.tyaguschev.view.Actions.ActionDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class InsertPositionDialog extends ActionDialog {
    private final PositionService positionService = new PositionService();
    private final DefaultTableModel tableModel;

    private JTextField positionNameField;
    private JTextField positionCostField;

    /**
     * Создание диалога создания должности
     * @param tableModel
     */
    public InsertPositionDialog(DefaultTableModel tableModel) {
        super(400, 180, "Insert Position");
        this.tableModel = tableModel;
        initializeUI();
        addButtons("Insert");
        setVisible(true);
    }

    /**
     * Функция для инициализации интерфейса
     */
    protected void initializeUI() {
        JLabel positionNameLabel = new JLabel("Position name:");
        JLabel positionCostLabel = new JLabel("Position cost:");

        positionNameField = new JTextField(20);
        positionCostField = new JTextField(20);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;
        addRow(inputPanel, gbc, row++, positionNameLabel, positionNameField);
        addRow(inputPanel, gbc, row, positionCostLabel, positionCostField);

        setLayout(new BorderLayout(10, 10));
        add(inputPanel, BorderLayout.CENTER);
    }

    /**
     * Действие для кнопки подтверждения (создание должности)
     */
    @Override
    protected void okAction() {
        if (positionNameField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(getParent(), "Position name must be not empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int cost;
        try {
            cost = Math.abs(Integer.parseInt(positionCostField.getText()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(getParent(), "Position cost must be integer!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (positionCostField.getText().isEmpty()) {
            positionCostField.setText("0");
        }
        Position position = new Position(
                positionNameField.getText(),
                cost
        );
        positionService.insert(position);

        positionService.setTableModel(tableModel);
    }
}
