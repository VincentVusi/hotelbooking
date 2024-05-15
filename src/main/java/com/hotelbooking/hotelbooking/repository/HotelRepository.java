package com.hotelbooking.hotelbooking.repository;

import com.hotelbooking.hotelbooking.model.Hotel;
import com.hotelbooking.hotelbooking.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByNameContaining(String name);
    List<Hotel> findByLocationContaining(String location);
    List<Hotel> findByPhoneNumberContaining(String phoneNumber);
}

