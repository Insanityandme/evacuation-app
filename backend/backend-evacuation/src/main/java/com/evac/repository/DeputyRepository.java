package com.evac.repository;

import com.evac.models.Deputy;
import com.evac.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeputyRepository extends JpaRepository<Deputy, Long> {

    Optional<Deputy> findByUsername(String username);
}

