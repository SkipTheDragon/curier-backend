package com.curier.Courier.Controllers;

import com.curier.Courier.Controllers.Exceptions.UserNotFoundException;
import com.curier.Courier.Models.User;
import com.curier.Courier.Models.User;
import com.curier.Courier.Repositories.UserRepository;
import com.curier.Courier.Security.UserDetails.UserDetailsImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserRepository repository;

    UserController(UserRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    List<User> all() {
        return repository.findAll();
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/user")
    User current(Authentication authentication) {
        Long currentUserID = ((UserDetailsImpl)authentication.getPrincipal()).getId();

        return repository.findById(currentUserID)
                .orElseThrow(() -> new UserNotFoundException(currentUserID));
    }

    // Single item
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        newUser.setPassword((new BCryptPasswordEncoder().encode(newUser.getPassword())));
        return repository.save(newUser);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{id}")
    User one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

//    @PutMapping("/users/{id}")
//    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {
//
//        return repository.findById(id)
//                .map(user -> {
//                    user.setUsername(newUser.getUsername());
//                    user.setRole(newUser.getRole());
//                    return repository.save(user);
//                })
//                .orElseGet(() -> {
//                    newUser.setId(id);
//                    return repository.save(newUser);
//                });
//    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
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