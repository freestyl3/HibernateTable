package ru.tyaguschev.view.Actions.Update;

import ru.tyaguschev.models.Position;
import ru.tyaguschev.services.PositionService;
import ru.tyaguschev.view.Actions.ActionDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UpdatePositionDialog extends ActionDialog {
    PositionService positionService = new PositionService();
    private final DefaultTableModel tableModel;

    JTextField idField = new JTextField(20);
    JTextField positionNameField;
    JTextField positionCostField;

    Object[] rowData;

    public UpdatePositionDialog(DefaultTableModel tableModel, Object[] rowData) {
        super(400, 200, "Update Position");
        this.tableModel = tableModel;
        this.rowData = rowData;
        initializeUI();
        addButtons("Update");
    }

    protected void initializeUI() {
        // Создание компонентов
        JLabel idLabel = new JLabel("Position ID:");
        JLabel positionNameLabel = new JLabel("Position name:");
        JLabel positionCostLabel = new JLabel("Position cost:");

        /**/

        idField.setText(rowData[0].toString());
        idField.setEditable(false);
        positionNameField = new JTextField(20);
        positionNameField.setText(rowData[1].toString());
        positionCostField = new JTextField(20);
        positionCostField.setText(rowData[2].toString());

        // Панель для полей ввода
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;
        addRow(inputPanel, gbc, row++, idLabel, idField);
        addRow(inputPanel, gbc, row++, positionNameLabel, positionNameField);
        addRow(inputPanel, gbc, row++, positionCostLabel, positionCostField);

        // Настройка основного контейнера
        setLayout(new BorderLayout(10, 10));
        add(inputPanel, BorderLayout.CENTER);
    }

    @Override
    protected void okAction() {
        Position position = positionService.findById(Integer.parseInt(idField.getText()));
        position.setPositionName(positionNameField.getText());
        position.setCost(Integer.parseInt(positionCostField.getText()));

        positionService.update(position);
        positionService.updateTable();

        positionService.setTableModel(tableModel);
    }
}
