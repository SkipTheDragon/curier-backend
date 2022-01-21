package com.curier.Courier.Repositories;

import com.curier.Courier.Models.Client;
import com.curier.Courier.Models.Employee;
import com.curier.Courier.Models.Shipment;
import com.curier.Courier.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    Optional<Shipment> findByClient_Id(Long clientID);
    Optional<List<Shipment>> findAllShipmentByClient_User(User user);
    Optional<List<Shipment>> findAllByEmployee(Employee employee);
    Optional<List<Shipment>> findAllByEmployeeIsNull();
    Optional<List<Shipment>> findAllByClient_User(User user);
    Optional<List<Shipment>> findAllByCreatedDateBetween(Timestamp timestampStart, Timestamp timestampEnd);
    Optional<Shipment> findByClientAndId(Client client, Long id);
}