package com.curier.Courier.Models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Client {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToOne
    private User user;
    private String firstName;
    private String lastName;
    @OneToMany
    private List<Shipment> shipments;

    public Client(Long id, String firstName, String lastName,  User user) {
        this.id = id;
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Client() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}