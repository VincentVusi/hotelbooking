package com.hotelbooking.hotelbooking.repository;

import com.hotelbooking.hotelbooking.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
}

