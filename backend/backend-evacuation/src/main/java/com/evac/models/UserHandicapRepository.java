package com.evac.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHandicapRepository extends JpaRepository<UserHandicap, Long> {

}
