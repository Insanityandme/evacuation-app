package com.evac.repository;

import com.evac.models.Notification;
import com.evac.models.Zone;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Optional<Notification> findByName(String name);

    Boolean existsByName(String name);

    @Override
    boolean existsById(Long aLong);
}
