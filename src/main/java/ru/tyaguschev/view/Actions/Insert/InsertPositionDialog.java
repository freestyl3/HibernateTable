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

    public InsertPositionDialog(DefaultTableModel tableModel) {
        super(400, 180, "Insert Position");
        this.tableModel = tableModel;
        initializeUI();
        addButtons("Insert");
    }

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

    @Override
    protected void okAction() {
        Position position = new Position(
                positionNameField.getText(),
                Integer.parseInt(positionCostField.getText())
        );
        positionService.insert(position);

        positionService.setTableModel(tableModel);
    }
}
