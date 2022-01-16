package com.curier.Courier.Models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Warehouse {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    private Location location;

    private String name;

    @OneToMany
    private List<Shipment> shipmentList;

    public Warehouse() {

    }

    public Warehouse(Long id, Location location, String name) {
        this.id = id;
        this.location = location;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}