package ru.tyaguschev.view.Actions.Insert;

import com.github.lgooddatepicker.components.DatePicker;
import ru.tyaguschev.models.Employee;
import ru.tyaguschev.models.Position;
import ru.tyaguschev.services.EmployeeService;
import ru.tyaguschev.services.PositionService;
import ru.tyaguschev.view.Actions.ActionDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.List;

public class InsertEmployeeDialog extends ActionDialog {
    private final EmployeeService employeeService = new EmployeeService();
    private final PositionService positionService = new PositionService();
    private final List<Integer> positionIds = positionService.findAllIds();
    private final DefaultTableModel tableModel;

    private JTextField firstnameField;
    private JTextField lastnameField;
    private JComboBox<String> positionIdCombo;
    private DatePicker datePicker;

    public InsertEmployeeDialog(DefaultTableModel tableModel) {
        super(400, 250, "Insert Employee");
        this.tableModel = tableModel;
        initializeUI();
        addButtons("Insert");
    }

    protected void initializeUI() {
        JLabel firstnameLabel = new JLabel("Firstname:");
        JLabel lastnameLabel = new JLabel("Lastname:");
        JLabel birthdayLabel = new JLabel("Birthday:");
        JLabel positionIdLabel = new JLabel("Position ID:");

        firstnameField = new JTextField(20);
        lastnameField = new JTextField(20);
        positionIdCombo = new JComboBox<>();
        for (int positionId : positionIds) {
            positionIdCombo.addItem(positionService.findById(positionId).getPositionName());
        }
        datePicker = new DatePicker();
        datePicker.setTextFieldToValidStateIfNeeded();
        datePicker.setDateToToday();

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;
        addRow(inputPanel, gbc, row++, firstnameLabel, firstnameField);
        addRow(inputPanel, gbc, row++, lastnameLabel, lastnameField);
        addRow(inputPanel, gbc, row++, birthdayLabel, datePicker);
        addRow(inputPanel, gbc, row, positionIdLabel, positionIdCombo);

        setLayout(new BorderLayout(10, 10));
        add(inputPanel, BorderLayout.CENTER);
    }

    @Override
    protected void okAction() {
        Employee employee = new Employee(
                firstnameField.getText(),
                lastnameField.getText(),
                Date.valueOf(datePicker.getDate())
        );
        Position position = positionService.findByPositionName(positionIdCombo.getSelectedItem().toString());
        position.addEmployee(employee);
        employee.setPosition(position);
        employeeService.insert(employee);

        employeeService.setTableModel(tableModel);
    }
}