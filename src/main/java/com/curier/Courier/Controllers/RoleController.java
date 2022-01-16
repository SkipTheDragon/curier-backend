package com.curier.Courier.Controllers;

import com.curier.Courier.Controllers.Exceptions.RoleNotFoundException;
import com.curier.Courier.Models.Role;
import com.curier.Courier.Repositories.RoleRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController {

    private final RoleRepository repository;

    RoleController(RoleRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/roles")
    List<Role> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/roles")
    Role newRole(@RequestBody Role newRole) {
        return repository.save(newRole);
    }

    // Single item
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/roles/{id}")
    Role one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(id));
    }

//    @PutMapping("/roles/{id}")
//    Role replaceRole(@RequestBody Role newRole, @PathVariable Long id) {
//
//        return repository.findById(id)
//                .map(role -> {
//                    role.setUsername(newRole.getUsername());
//                    role.setRole(newRole.getRole());
//                    return repository.save(role);
//                })
//                .orElseGet(() -> {
//                    newRole.setId(id);
//                    return repository.save(newRole);
//                });
//    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/roles/{id}")
    void deleteRole(@PathVariable Long id) {
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