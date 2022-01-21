package com.curier.Courier.Controllers;

import com.curier.Courier.Controllers.Exceptions.ClientNotFoundException;
import com.curier.Courier.Controllers.Exceptions.ShipmentNotFoundException;
import com.curier.Courier.Controllers.Exceptions.UserNotFoundException;
import com.curier.Courier.Models.Client;
import com.curier.Courier.Models.Employee;
import com.curier.Courier.Models.Shipment;
import com.curier.Courier.Models.User;
import com.curier.Courier.Repositories.*;
import com.curier.Courier.Security.UserDetails.UserDetailsImpl;
import com.curier.Courier.Statistics;
import com.curier.Courier.Utilities.PDFExporter;
import com.lowagie.text.DocumentException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class ShipmentController {

    private final ShipmentRepository repository;

    private final UserRepository userRepository;

    private final ClientRepository clientRepository;

    private final WarehouseRepository warehouseRepository;

    private final EmployeeRepository employeeRepository;

    public ShipmentController(ShipmentRepository repository,
                              UserRepository userRepository,
                              ClientRepository clientRepository,
                              WarehouseRepository warehouseRepository,
                              EmployeeRepository employeeRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.warehouseRepository = warehouseRepository;
        this.employeeRepository = employeeRepository;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/shipments")
    List<Shipment> all() {
        return repository.findAll();
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/shipments/free")
    List<Shipment> shipmentsAvailable() {
        return repository.findAllByEmployeeIsNull().orElseThrow(() -> new ShipmentNotFoundException("none available at the moment"));
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/shipments/taken")
    List<Shipment> myShipmentsEmployee(Authentication authentication) {
        Long currentUserID = ((UserDetailsImpl)authentication.getPrincipal()).getId();
        User currentUser = userRepository.findById(currentUserID).orElseThrow(() -> new UserNotFoundException(currentUserID));
        Employee employee = employeeRepository.findByUser(currentUser);
        return repository.findAllByEmployee(employee).orElseThrow(() -> new ShipmentNotFoundException("Has no shipments"));
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/shipments/{id}/take-order")
    Shipment takeShipment(@PathVariable Long id, Authentication authentication) {
        Shipment shipment = repository.findById(id).orElseThrow(() -> new ShipmentNotFoundException("No"));
        Long currentUserID = ((UserDetailsImpl)authentication.getPrincipal()).getId();
        User currentUser = userRepository.findById(currentUserID).orElseThrow(() -> new UserNotFoundException(currentUserID));
        Employee employee = employeeRepository.findByUser(currentUser);
        shipment.setEmployee(employee);
        return repository.save(shipment);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/shipments/{id}/changestatus")
    Shipment myShipmentsEmployee(@PathVariable Long id, Authentication authentication) {
        Optional<Shipment> shipment =repository.findById(id);
        Shipment shipment1 = shipment.get();
        shipment1.setStatus(shipment1.getStatus() +1   );
        return repository.save(shipment1);
    }

    // end::get-aggregate-root[]
    // Single item

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/stats")
    Statistics statistics() {
        Statistics statistics = new Statistics(
                userRepository.count(),
                repository.count(),
                warehouseRepository.count());

        return statistics;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/shipments")
    Shipment newOrder(@RequestBody Shipment newShipment, Authentication authentication) {
        Long currentUserID = ((UserDetailsImpl)authentication.getPrincipal()).getId();
        User currentUser = userRepository.findById(currentUserID).orElseThrow(() -> new UserNotFoundException(currentUserID));
        Client client = clientRepository.findByUser(currentUser).orElseThrow(() -> new ClientNotFoundException("By current user"));
        newShipment.setClient(client);
        return repository.save(newShipment);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/shipments/cancel/{id}")
    Shipment cancelOrder(@PathVariable Long id, Authentication authentication) {
        Long currentUserID = ((UserDetailsImpl)authentication.getPrincipal()).getId();
        User currentUser = userRepository.findById(currentUserID).orElseThrow(() -> new UserNotFoundException(currentUserID));
        Client client = clientRepository.findByUser(currentUser).orElseThrow(() -> new ClientNotFoundException("By current user"));

        Shipment shipment = repository.findByClientAndId(client,id)
                .orElseThrow(() -> new ShipmentNotFoundException(id));
        shipment.setStatus(0);

        return repository.save(shipment);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/myshipments")
    List<Shipment> getOwnedShipments(Authentication authentication) {
        Long currentUserID = ((UserDetailsImpl)authentication.getPrincipal()).getId();
        User currentUser = userRepository.findById(currentUserID).orElseThrow(() -> new UserNotFoundException(currentUserID));
        return repository.findAllByClient_User(currentUser).orElseThrow(() -> new ShipmentNotFoundException("Has no shipments"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/shipments/between/{timestampStart}/{timestampEnd}")
    List<Shipment> getShipmentsBetween(@PathVariable Timestamp timestampStart, @PathVariable Timestamp timestampEnd) {
        return repository.findAllByCreatedDateBetween(timestampStart, timestampEnd).orElseThrow(() -> new ShipmentNotFoundException("Has no shipments"));
    }

    // Single item
    @PreAuthorize("hasRole('USER') or hasRole('EMPLOYEE') or hasRole('ADMIN')")
    @GetMapping("/shipments/{id}")
    Shipment one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ShipmentNotFoundException(id));
    }

//    @PutMapping("/shipments/{id}")
//    Order replaceOrder(@RequestBody Order newOrder, @PathVariable Long id) {
//
//        return repository.findById(id)
//                .map(order -> {
//                    order.setUsername(newOrder.getUsername());
//                    order.setRole(newOrder.getRole());
//                    return repository.save(order);
//                })
//                .orElseGet(() -> {
//                    newOrder.setId(id);
//                    return repository.save(newOrder);
//                });
//    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/shipments/{id}")
    void deleteOrder(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @GetMapping("/shipments/export/")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Shipment> listUsers = repository.findAll();

        PDFExporter<Shipment> exporter = new PDFExporter<Shipment>(listUsers);
        exporter.export(response);

    }
}