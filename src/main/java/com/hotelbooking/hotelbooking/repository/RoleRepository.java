package com.hotelbooking.hotelbooking.repository;

import com.hotelbooking.hotelbooking.model.Role;
import com.hotelbooking.hotelbooking.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}

