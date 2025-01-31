package ru.tyaguschev.services;

import ru.tyaguschev.models.Model;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public interface DatabaseService {
    Model findById(int id);
    void insert(Model model);
    void update(Model model);
    void delete(int id);
    void setTableModel(DefaultTableModel tableModel);
    DefaultTableModel getTableModel();
    void updateTable();
    void openInsertDialog();
    void openUpdateDialog(Object[] rowData);
    List<?> findAll();
}
