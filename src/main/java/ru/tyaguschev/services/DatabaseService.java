package ru.tyaguschev.services;

import ru.tyaguschev.models.Model;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public interface DatabaseService {
    void insert(Model model);
    void update(Model model);
    void delete(int id);
    DefaultTableModel getTableModel();
    void updateTable();
    void openInsertDialog();
    void openUpdateDialog(Object[] rowData);
    List<?> findAll();
    List<String> getColumns();
}
