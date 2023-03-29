package com.example.springbootsecuritypostgresql.repository;

import com.example.springbootsecuritypostgresql.models.ERole;
import com.example.springbootsecuritypostgresql.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository  extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
