package ru.tyaguschev.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "positions")
public class Position implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees;

    private String position_name;
    private int cost;

    public Position() {}

    public Position(
            String position_name,
            int cost
    ) {
        this.position_name = position_name;
        this.cost = cost;
        employees = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employee.setPosition(this);
        employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public String getPositionName() {
        return position_name;
    }

    public void setPositionName(String position_name) {
        this.position_name = position_name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString(){
        return id + "";
    }

}
