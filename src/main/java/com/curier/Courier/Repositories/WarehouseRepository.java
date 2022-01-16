package com.curier.Courier.Repositories;

import com.curier.Courier.Models.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
//    Employee findByUsername(String username);
}