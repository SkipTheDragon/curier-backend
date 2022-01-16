package com.curier.Courier;

public class Statistics {
    public Long users = 0L;
    public Long orders= 0L;
    public Long warehouses = 0L;

    public Statistics(Long users, Long orders, Long warehouses) {
        this.users = users;
        this.orders = orders;
        this.warehouses = warehouses;
    }

    public Long getUsers() {
        return users;
    }

    public Long getOrders() {
        return orders;
    }

    public Long getWarehouses() {
        return warehouses;
    }
}
