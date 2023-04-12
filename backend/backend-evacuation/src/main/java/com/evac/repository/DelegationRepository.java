package com.evac.repository;

import java.util.Optional;

import com.evac.models.Delegation;
import com.evac.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evac.models.ERole;
import com.evac.models.Role;

@Repository
public interface DelegationRepository extends JpaRepository<Delegation, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
}