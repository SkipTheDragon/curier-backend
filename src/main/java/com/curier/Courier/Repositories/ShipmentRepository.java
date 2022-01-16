package com.curier.Courier.Repositories;

import com.curier.Courier.Models.Client;
import com.curier.Courier.Models.Shipment;
import com.curier.Courier.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    Optional<Shipment> findByClient_Id(Long clientID);
    Optional<List<Shipment>> findAllShipmentByClient_User(User user);
    Optional<List<Shipment>> findAllByClient_User(User user);
}