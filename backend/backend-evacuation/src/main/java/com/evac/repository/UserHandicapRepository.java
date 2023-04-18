package com.evac.repository;

import com.evac.models.UserHandicap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHandicapRepository extends JpaRepository<UserHandicap, Long> {

}
