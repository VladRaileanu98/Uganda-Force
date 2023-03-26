package com.example.backend.repository;

import com.example.backend.model.ERole;
import com.example.backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role getRoleByName(String name);

    Optional<Role> findByName(ERole name);

    Role getRoleById(Integer roleId);
}
