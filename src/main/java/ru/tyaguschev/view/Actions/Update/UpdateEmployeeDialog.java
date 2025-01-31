package ru.tyaguschev.view.Actions.Update;

import com.github.lgooddatepicker.components.DatePicker;
import ru.tyaguschev.models.Employee;
import ru.tyaguschev.models.Position;
import ru.tyaguschev.services.EmployeeService;
import ru.tyaguschev.services.PositionService;
import ru.tyaguschev.view.Actions.ActionDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class UpdateEmployeeDialog extends ActionDialog {
    private final EmployeeService employeeService = new EmployeeService();
    private final PositionService positionService = new PositionService();
    private final List<Integer> positionIds = positionService.findAllIds();
    private final DefaultTableModel tableModel;

    private final JTextField idField = new JTextField(20);
    private JTextField firstnameField;
    private JTextField lastnameField;
    private JComboBox<String> positionIdCombo;
    private DatePicker datePicker;

    private final Object[] rowData;

    public UpdateEmployeeDialog(DefaultTableModel tableModel, Object[] rowData) {
        super(400, 300, "Update Employee");
        this.tableModel = tableModel;
        this.rowData = rowData;
        initializeUI();
        addButtons("Update");
    }

    protected void initializeUI() {
        // Создание компонентов
        JLabel idLabel = new JLabel("Employee ID:");
        JLabel firstnameLabel = new JLabel("Firstname:");
        JLabel lastnameLabel = new JLabel("Lastname:");
        JLabel birthdayLabel = new JLabel("Birthday:");
        JLabel positionIdLabel = new JLabel("Position ID:");

        /**/


        idField.setText(rowData[0].toString());/* вставить id */
        idField.setEditable(false);
        firstnameField = new JTextField(20);
        firstnameField.setText(rowData[1].toString());
        lastnameField = new JTextField(20);
        lastnameField.setText(rowData[2].toString());
        positionIdCombo = new JComboBox<>();
        for (int positionId : positionIds) {
            positionIdCombo.addItem(positionService.findById(positionId).getPositionName());
        }
        positionIdCombo.setSelectedItem(rowData[4].toString());
        datePicker = new DatePicker();
        datePicker.setTextFieldToValidStateIfNeeded();
        datePicker.setDate(LocalDate.parse(rowData[3].toString().substring(0, 10)));

//        System.out.println(rowData[0] + ", " + rowData[1] + ", " + rowData[2] + ", " + rowData[3] + ", " + rowData[4]);

        // Панель для полей ввода
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;
        addRow(inputPanel, gbc, row++, idLabel, idField);
        addRow(inputPanel, gbc, row++, firstnameLabel, firstnameField);
        addRow(inputPanel, gbc, row++, lastnameLabel, lastnameField);
        addRow(inputPanel, gbc, row++, birthdayLabel, datePicker);
        addRow(inputPanel, gbc, row, positionIdLabel, positionIdCombo);

        // Настройка основного контейнера
        setLayout(new BorderLayout(10, 10));
        add(inputPanel, BorderLayout.CENTER);
    }

    @Override
    protected void okAction() {
        Employee employee = employeeService.findById(Integer.parseInt(idField.getText()));
        employee.setFirstname(firstnameField.getText());
        employee.setLastname(lastnameField.getText());
        employee.setBirthday(Date.valueOf(datePicker.getDate()));
        Position position = positionService.findByPositionName(positionIdCombo.getSelectedItem().toString());
        position.addEmployee(employee);
        employee.setPosition(position);

        employeeService.update(employee);
        positionService.update(position);

        employeeService.setTableModel(tableModel);
    }
}
