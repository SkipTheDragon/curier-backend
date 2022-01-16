package com.curier.Courier.Repositories;

import com.curier.Courier.Models.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
//    Employee findByUsername(String username);
}