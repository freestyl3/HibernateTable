package ru.tyaguschev.services;

import ru.tyaguschev.dao.PositionDAO;
import ru.tyaguschev.models.Employee;
import ru.tyaguschev.models.Model;
import ru.tyaguschev.models.Position;
import ru.tyaguschev.view.Actions.Insert.InsertPositionDialog;
import ru.tyaguschev.view.Actions.Update.UpdatePositionDialog;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class PositionService implements DatabaseService{
    private final PositionDAO positionsDAO = new PositionDAO();
    private DefaultTableModel tableModel = new DefaultTableModel();

    public PositionService() {}

    @Override
    public Position findById(int id) {
        return positionsDAO.findById(id);
    }

    @Override
    public void insert(Model position) {
        positionsDAO.insert(position);
        updateTable();
    }

    @Override
    public void update(Model position) {
        positionsDAO.update(position);
        updateTable();
    }

    @Override
    public void delete(int id) {
        positionsDAO.delete(findById(id));
        updateTable();
    }

    public List<Integer> findAllIds() {return positionsDAO.findAllIds();}

    public Position findByPositionName(String name) {return positionsDAO.findByPositionName(name);}

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
    public void updateTable(){
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        List<Position> positions = positionsDAO.findAll();

        tableModel.addColumn("Position ID");
        tableModel.addColumn("Position name");
        tableModel.addColumn("Position cost");

        if (!positions.isEmpty()) {
            for (Position position : positions) {
                tableModel.addRow(getRowData(position));
            }
        }
    }

    /**
     * Функция получения значений в строке таблицы
     * @param position
     * @return
     */
    private Object[] getRowData(Position position) {
        return (new Object[]{
                position.getId(),
                position.getPositionName(),
                position.getCost()
        });
    }

    /**
     * Функция для открытия диалога создания
     */
    @Override
    public void openInsertDialog() {
        new InsertPositionDialog(tableModel);
    }

    /**
     * Функция для открытия диалога редактирования
     * @param rowData
     */
    @Override
    public void openUpdateDialog(Object[] rowData) {
        new UpdatePositionDialog(tableModel, rowData);
    }

    @Override
    public List<Position> findAll() {
        return positionsDAO.findAll();
    }

    public Employee findEmployeeById(int id) {
        return positionsDAO.findEmployeeById(id);
    }
}
