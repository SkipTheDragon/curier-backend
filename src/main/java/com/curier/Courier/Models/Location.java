package com.curier.Courier.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Map;

@Entity
public class Location {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private Integer coordY;
    private Integer coordX;

    public Location(Long id, Integer coordY, Integer coordX, String name) {
        this.id = id;
        this.coordY = coordY;
        this.coordX = coordX;
        this.name = name;
    }

    private String name;

    public Location() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCoordY() {
        return coordY;
    }

    public void setCoordY(Integer coordY) {
        this.coordY = coordY;
    }

    public Integer getCoordX() {
        return coordX;
    }

    public void setCoordX(Integer coordX) {
        this.coordX = coordX;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}