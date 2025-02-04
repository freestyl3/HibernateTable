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

    @Override
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

    @Override
    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
        updateTable();
    }

    /**
     * Функция для динамического обновления таблицы
     */
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

    /**
     * Функция получения значений в строке таблицы
     * @param employee
     * @return
     */
    private Object[] getRowData(Employee employee) {
        return (new Object[]{
                employee.getId(),
                employee.getFirstname(),
                employee.getLastname(),
                LocalDate.parse(employee.getBirthday().toString().substring(0, 10)),
                new PositionService().findById(employee.getPosition().getId()).getPositionName()
        });
    }

    /**
     * Функция для открытия диалога создания
     */
    @Override
    public void openInsertDialog() {
        new InsertEmployeeDialog(tableModel);
    }

    /**
     * Функция для открытия диалога редактирования
     * @param rowData
     */
    @Override
    public void openUpdateDialog(Object[] rowData) {
        new UpdateEmployeeDialog(tableModel, rowData);
    }

    @Override
    public List<Employee> findAll() {
        return employeeDAO.findAll();
    }

}
