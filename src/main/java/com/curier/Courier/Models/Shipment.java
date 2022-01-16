package com.curier.Courier.Models;

import javax.persistence.*;

@Entity
public class Shipment {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Warehouse toWarehouse;

    @OneToOne
    private Warehouse fromWarehouse;

    private float price;

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    private float mass;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Employee employee;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    private Integer status;
    
    public Shipment(Warehouse toWarehouse, Warehouse fromWarehouse, float price, Client client, Employee employee, Integer status, float mass) {
        this.toWarehouse = toWarehouse;
        this.fromWarehouse = fromWarehouse;
        this.price = price;
        this.client = client;
        this.employee = employee;
        this.status = status;
        this.mass = mass;
    }
    public Shipment(Warehouse toWarehouse, Warehouse fromWarehouse, float price, Client client, Integer status, float mass) {
        this.toWarehouse = toWarehouse;
        this.fromWarehouse = fromWarehouse;
        this.price = price;
        this.client = client;
        this.status = status;
        this.mass = mass;
    }
    public Shipment(Long id, Warehouse toWarehouse, Warehouse fromWarehouse, float price, Client client, Employee employee, Integer status, float mass) {
        this(toWarehouse, fromWarehouse, price, client, employee, status, mass);
        this.id = id;
    }

    public Shipment() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Warehouse getToWarehouse() {
        return toWarehouse;
    }

    public void setToWarehouse(Warehouse toWarehouse) {
        this.toWarehouse = toWarehouse;
    }

    public Warehouse getFromWarehouse() {
        return fromWarehouse;
    }

    public void setFromWarehouse(Warehouse fromWarehouse) {
        this.fromWarehouse = fromWarehouse;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}