package com.hotelbooking.hotelbooking.repository;

import com.hotelbooking.hotelbooking.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
