package ru.tyaguschev.dao;

import ru.tyaguschev.models.Model;

import java.util.List;

/**
 * Интерфейс для контроля доступа к базе данных
 */
public interface DataAccesObject {
    void insert(Model model);
    void update(Model model);
    void delete(Model model);
    List<?> findAll();
    List<String> getColumns();
}
