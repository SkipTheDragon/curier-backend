package com.curier.Courier.Controllers;

import com.curier.Courier.Controllers.Exceptions.LocationNotFoundException;
import com.curier.Courier.Models.Location;
import com.curier.Courier.Repositories.LocationRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LocationController {

    private final LocationRepository repository;

    LocationController(LocationRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/locations")
    List<Location> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/locations")
    Location newLocation(@RequestBody Location newLocation) {
        return repository.save(newLocation);
    }

    // Single item
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/locations/{id}")
    Location one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new LocationNotFoundException(id));
    }

//    @PutMapping("/locations/{id}")
//    Location replaceLocation(@RequestBody Location newLocation, @PathVariable Long id) {
//
//        return repository.findById(id)
//                .map(location -> {
//                    location.setUsername(newLocation.getUsername());
//                    location.setRole(newLocation.getRole());
//                    return repository.save(location);
//                })
//                .orElseGet(() -> {
//                    newLocation.setId(id);
//                    return repository.save(newLocation);
//                });
//    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/locations/{id}")
    void deleteLocation(@PathVariable Long id) {
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