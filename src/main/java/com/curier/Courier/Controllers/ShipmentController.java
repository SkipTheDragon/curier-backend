package com.curier.Courier.Controllers;

import com.curier.Courier.Controllers.Exceptions.ClientNotFoundException;
import com.curier.Courier.Controllers.Exceptions.ShipmentNotFoundException;
import com.curier.Courier.Controllers.Exceptions.UserNotFoundException;
import com.curier.Courier.Models.Client;
import com.curier.Courier.Models.Shipment;
import com.curier.Courier.Models.User;
import com.curier.Courier.Repositories.ClientRepository;
import com.curier.Courier.Repositories.ShipmentRepository;
import com.curier.Courier.Repositories.UserRepository;
import com.curier.Courier.Repositories.WarehouseRepository;
import com.curier.Courier.Security.UserDetails.UserDetailsImpl;
import com.curier.Courier.Statistics;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShipmentController {

    private final ShipmentRepository repository;

    private final UserRepository userRepository;

    private final ClientRepository clientRepository;

    private final WarehouseRepository warehouseRepository;

    public ShipmentController(ShipmentRepository repository,
                              UserRepository userRepository,
                              ClientRepository clientRepository,
                              WarehouseRepository warehouseRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.warehouseRepository = warehouseRepository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/shipments")
    List<Shipment> all() {
        return repository.findAll();
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

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/shipments")
    Shipment newOrder(@RequestBody Shipment newShipment, Authentication authentication) {
        Long currentUserID = ((UserDetailsImpl)authentication.getPrincipal()).getId();
        User currentUser = userRepository.findById(currentUserID).orElseThrow(() -> new UserNotFoundException(currentUserID));
        Client client = clientRepository.findByUser(currentUser).orElseThrow(() -> new ClientNotFoundException("By current user"));
        newShipment.setClient(client);
        return repository.save(newShipment);
    }
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/myshipments")
    List<Shipment> getOwnedShipments(Authentication authentication) {
        Long currentUserID = ((UserDetailsImpl)authentication.getPrincipal()).getId();
        User currentUser = userRepository.findById(currentUserID).orElseThrow(() -> new UserNotFoundException(currentUserID));
        return repository.findAllByClient_User(currentUser).orElseThrow(() -> new ShipmentNotFoundException("Has no shipments"));
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

//    @GetMapping("/users/export/pdf")
//    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
//        response.setContentType("application/pdf");
//        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
//        String currentDateTime = dateFormatter.format(new Date());
//
//        String headerKey = "Content-Disposition";
//        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
//        response.setHeader(headerKey, headerValue);
//
//        List<User> listUsers = service.listAll();
//
//        UserPDFExporter exporter = new UserPDFExporter(listUsers);
//        exporter.export(response);
//
//    }
}