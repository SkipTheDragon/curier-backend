package com.curier.Courier.Controllers;

import com.curier.Courier.Controllers.Exceptions.WarehouseNotFoundException;
import com.curier.Courier.Models.Warehouse;
import com.curier.Courier.Repositories.WarehouseRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WarehouseController {

    private final WarehouseRepository repository;

    WarehouseController(WarehouseRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/warehouses")
    List<Warehouse> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/warehouses")
    Warehouse newWarehouse(@RequestBody Warehouse newWarehouse) {
        return repository.save(newWarehouse);
    }

    // Single item

    @GetMapping("/warehouses/{id}")
    Warehouse one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new WarehouseNotFoundException(id));
    }

//    @PutMapping("/warehouses/{id}")
//    Warehouse replaceWarehouse(@RequestBody Warehouse newWarehouse, @PathVariable Long id) {
//
//        return repository.findById(id)
//                .map(warehouse -> {
//                    warehouse.setUsername(newWarehouse.getUsername());
//                    warehouse.setRole(newWarehouse.getRole());
//                    return repository.save(warehouse);
//                })
//                .orElseGet(() -> {
//                    newWarehouse.setId(id);
//                    return repository.save(newWarehouse);
//                });
//    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/warehouses/{id}")
    void deleteWarehouse(@PathVariable Long id) {
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