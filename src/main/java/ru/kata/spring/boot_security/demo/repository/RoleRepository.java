package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Set<Role> findByName(String role);

}
