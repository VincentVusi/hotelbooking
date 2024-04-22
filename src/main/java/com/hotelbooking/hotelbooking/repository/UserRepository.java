package com.hotelbooking.hotelbooking.repository;

import com.hotelbooking.hotelbooking.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByEmail(String email);
}
