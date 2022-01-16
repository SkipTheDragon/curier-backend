package com.curier.Courier;

import com.curier.Courier.Controllers.EmployeeController;
import com.curier.Courier.Models.*;
import com.curier.Courier.Repositories.*;
import com.curier.Courier.Security.ERole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Models:
 * Locations
 * Warehouses
 * Clients
 * Orders
 *
 * Functions:
 * Export as PDF
 */
@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository,
                                   UserRepository userRepository,
                                   ClientRepository clientRepository,
                                   WarehouseRepository warehouseRepository,
                                   LocationRepository locationRepository,
                                   RoleRepository roleRepository,
                                   ShipmentRepository shipmentRepository
    ) {

        Role role_user = new Role(ERole.ROLE_USER);
        Role role_employee = new Role(ERole.ROLE_EMPLOYEE);
        Role role_admin = new Role(ERole.ROLE_ADMIN);

        roleRepository.save(role_user);
        roleRepository.save(role_employee);
        roleRepository.save(role_admin);

        HashSet roles = new HashSet<Role>();

        roles.add(role_user);
        User user_client = new User("client",new BCryptPasswordEncoder().encode("pass"));
        user_client.setRoles(roles);
        userRepository.save(user_client);

        roles.add(role_employee);
        User user_curier = new User("curier",new BCryptPasswordEncoder().encode("pass"));
        user_curier.setRoles(roles);
        userRepository.save(user_curier);

        roles.add(role_admin);
        User user = new User("admin",new BCryptPasswordEncoder().encode("pass"));
        user.setRoles(roles);
        userRepository.save(user);

        employeeRepository.save(new Employee("Ion", "Priceputu", user));
        employeeRepository.save(new Employee("Cezar", "Vrabie", user_curier));

        clientRepository.save(new Client(1L, "Andrei", "Talpa Iute", user_client));
        clientRepository.save(new Client(2L, "Test", "Test Iute", user));

        Location sediuA = new Location(1L,0,0,"Bucuresti");
        Location sediuB = new Location(2L,250,250,"Galati");
        Location sediuC = new Location(3L,-450,50,"Cluj");

        locationRepository.save(sediuA);
        locationRepository.save(sediuB);
        locationRepository.save(sediuC);
        Warehouse wA = new Warehouse(1L, sediuA, "Sediu A");
        Warehouse wB = new Warehouse(2L, sediuB, "Sediu B");
        Warehouse wC = new Warehouse(3L, sediuC, "Sediu C");
        warehouseRepository.save(wA);
        warehouseRepository.save(wB);
        warehouseRepository.save(wC);
//        shipmentRepository.save(new Shipment(wA,wB, 20,user,0, 20)
        return args -> {
            log.info("Database Init.");

        };
    }
}