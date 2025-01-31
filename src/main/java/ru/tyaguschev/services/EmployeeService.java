package ru.tyaguschev.services;

import ru.tyaguschev.models.Employee;
import ru.tyaguschev.dao.EmployeeDAO;
import ru.tyaguschev.models.Model;
import ru.tyaguschev.view.Actions.Insert.InsertEmployeeDialog;
import ru.tyaguschev.view.Actions.Update.UpdateEmployeeDialog;

import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.util.List;

public class EmployeeService implements DatabaseService{
    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private DefaultTableModel tableModel = new DefaultTableModel();

    public Employee findById(int id) {
        return employeeDAO.findById(id);
    }

    @Override
    public void insert(Model employee) {
        employeeDAO.insert(employee);
        updateTable();
    }

    @Override
    public void update(Model employee) {
        employeeDAO.update(employee);
        updateTable();
    }

    @Override
    public void delete(int id) {
        employeeDAO.delete(findById(id));
        updateTable();
    }

    @Override
    public DefaultTableModel getTableModel() {
        updateTable();
        return tableModel;
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
        updateTable();
    }

    @Override
    public void updateTable() {
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        List<Employee> employees = employeeDAO.findAll();

        tableModel.addColumn("Employee ID");
        tableModel.addColumn("Firstname");
        tableModel.addColumn("Lastname");
        tableModel.addColumn("Birthday");
        tableModel.addColumn("Position name");

        if (!employees.isEmpty()) {
            for (Employee employee : employees) {
                tableModel.addRow(getRowData(employee));
            }
        }
    }

    public Object[] getRowData(Employee employee) {
        return (new Object[]{
                employee.getId(),
                employee.getFirstname(),
                employee.getLastname(),
                LocalDate.parse(employee.getBirthday().toString().substring(0, 10)),
                new PositionService().findById(employee.getPosition().getId()).getPositionName()
        });
    }

    @Override
    public void openInsertDialog() {
        InsertEmployeeDialog insertDialog = new InsertEmployeeDialog(tableModel);
        insertDialog.setVisible(true);
    }

    @Override
    public void openUpdateDialog(Object[] rowData) {
        UpdateEmployeeDialog updateDialog = new UpdateEmployeeDialog(tableModel, rowData);
        updateDialog.setVisible(true);
    }

    @Override
    public List<Employee> findAll() {
        return employeeDAO.findAll();
    }

    @Override
    public List<String> getColumns() {
        return employeeDAO.getColumns();
    }
}
