package com.hotelbooking.hotelbooking.repository;

import com.hotelbooking.hotelbooking.model.AvailableRoom;
import com.hotelbooking.hotelbooking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByRoomId(Long roomId);

    List<Booking> findByAppUserId(Long appUserid);
}

