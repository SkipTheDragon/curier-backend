package com.curier.Courier.Repositories;

import com.curier.Courier.Models.Client;
import com.curier.Courier.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
//    Employee findByUsername(String username);
    Optional<Client> findByUser(User user);
}