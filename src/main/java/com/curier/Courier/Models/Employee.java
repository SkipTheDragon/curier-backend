package com.curier.Courier.Models;

import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
public class Employee{

    private @Id @GeneratedValue Long id;
    private String firstName;
    private String lastName;
    @OneToOne
    private User user;

    @OneToMany
    private List<Shipment> shipments;
    public  Employee() {}
    public Employee(String firstName, String lastName, User user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.user = user;
    }
    public Employee(Long id, String firstName, String lastName, User user) {
        this(firstName, lastName, user);
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Employee))
            return false;
        Employee employee = (Employee) o;
        return Objects.equals(this.id, employee.id);
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + this.id + "'}'";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Shipment> getOrders() {
        return shipments;
    }

    public void setOrders(List<Shipment> shipments) {
        this.shipments = shipments;
    }
}