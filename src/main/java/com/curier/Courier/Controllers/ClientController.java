package com.curier.Courier.Controllers;

import com.curier.Courier.Controllers.Exceptions.ClientNotFoundException;
import com.curier.Courier.Models.Client;
import com.curier.Courier.Repositories.ClientRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientController {

    private final ClientRepository repository;

    ClientController(ClientRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    @GetMapping("/clients")
    List<Client> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/clients")
    Client newClient(@RequestBody Client newClient) {
        return repository.save(newClient);
    }

    // Single item
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    @GetMapping("/clients/{id}")
    Client one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }

//    @PutMapping("/clients/{id}")
//    Client replaceClient(@RequestBody Client newClient, @PathVariable Long id) {
//
//        return repository.findById(id)
//                .map(client -> {
//                    client.setUsername(newClient.getUsername());
//                    client.setRole(newClient.getRole());
//                    return repository.save(client);
//                })
//                .orElseGet(() -> {
//                    newClient.setId(id);
//                    return repository.save(newClient);
//                });
//    }

    @DeleteMapping("/clients/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    void deleteClient(@PathVariable Long id) {
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